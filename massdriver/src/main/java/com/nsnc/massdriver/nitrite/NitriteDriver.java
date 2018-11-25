package com.nsnc.massdriver.nitrite;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.FileAsset;
import com.nsnc.massdriver.chunk.*;
import com.nsnc.massdriver.chunk.storage.FragmentPathStorage;
import com.nsnc.massdriver.crypt.KeyPairPool;
import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.driver.InterruptedChunkStreamException;
import com.nsnc.massdriver.driver.NoSuchAssetException;
import com.nsnc.massdriver.nitrite.entity.NitriteAsset;
import com.nsnc.massdriver.nitrite.entity.NitriteChunkMetadata;
import com.nsnc.massdriver.nitrite.entity.NitriteLocation;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NitriteDriver implements Driver {

    private static final Logger LOGGER = Logger.getLogger(NitriteDriver.class.getName());
    private static final String TRAITS_FIELD = "traits";
    private static final String CHUNK_METADATA_FIELD = "chunkMetadata";

    private final Path fragmentPath;
    private final Nitrite nitrite;
    private final ObjectRepository<NitriteAsset> assetRepository;
    private final ObjectRepository<NitriteChunkMetadata> metadataRepository;
    private final ObjectRepository<NitriteLocation> locationRepository;

    public NitriteDriver(Path fragmentPath, Nitrite nitrite) {
        this.fragmentPath = fragmentPath;
        this.nitrite = nitrite;
        this.assetRepository = nitrite.getRepository(NitriteAsset.class);
        this.metadataRepository = nitrite.getRepository(NitriteChunkMetadata.class);
        this.locationRepository = nitrite.getRepository(NitriteLocation.class);
    }


    @Override
    public Asset encrypt(Asset asset, KeyPairPool KeyPairPool) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Asset descrypt(Asset asset, KeyPairPool KeyPairPool) {
        throw new UnsupportedOperationException();
    }


    @Override
    public List<Trait> persistAsset(Asset asset) throws IOException {
        LOGGER.info("Persist Asset: " + asset);
        Optional<Asset> existingAsset = retrieveAsset(asset.getTraits());

        if (existingAsset.isPresent()) {
            LOGGER.info("Existing Asset Found: " + existingAsset.get());
            return existingAsset.get().getTraits();
        }
        NitriteAsset nitriteAsset = new NitriteAsset(asset);
        assetRepository.insert(nitriteAsset);

        if (asset instanceof FileAsset) {
            LOGGER.info("Recording FileAsset Chunk Locations");
            Path path = ((FileAsset) asset).getPath();
            nitriteAsset.getChunkMetadata().stream()
                    .map(chunkMetadata -> new NitriteLocation(chunkMetadata, path))
                    .forEach(locationRepository::insert);

        }
        nitrite.commit();
        return nitriteAsset.getTraits();
    }

    @Override
    public List<Trait> removeAsset(Asset asset) {
        retrieveAsset(asset.getTraits())
                .filter(NitriteAsset.class::isInstance)
                .map(NitriteAsset.class::cast)
                .ifPresent(assetRepository::remove);
        return asset.getTraits();
    }

    @Override
    public Optional<Asset> retrieveAsset(List<Trait> traits) {
        return findAssetsByTraits(traits.toArray(new Trait[]{})).findFirst();
    }

    @Override
    public Stream<Asset> findAssetsByTraits(Trait... traits) {
        LOGGER.info("Find Assets By Traits:");
        LOGGER.info(Stream.of(traits
        ).map(Trait::toString).collect(Collectors.joining(";")));
        return StreamSupport
                .stream(assetRepository
                        .find(ofTraits(Stream.of(traits)))
                        .spliterator(), false)
            .map(Asset.class::cast);
    }

    @Override
    public Stream<Asset> allAssets() {
        return StreamSupport.stream(assetRepository.find().spliterator(), false)
                .map(Asset.class::cast);
    }

    @Override
    public ChunkMetadata persistChunk(Chunk chunk) throws IOException {
        Optional<Chunk> existing = retrieveChunk(chunk);
        if (existing.isPresent()) {
            return existing.get();
        }
        return newFragment(chunk);
    }

    @Override
    public Optional<Location> chunkLocation(List<Trait> chunkTraits) {
        return Optional.of(chunkTraits)
                .map(List::stream)
                .map(NitriteDriver::ofTraits)
                .map(locationRepository::find)
                .map(Cursor::firstOrDefault);
    }

    @Override
    public Optional<Chunk> retrieveChunk(ChunkMetadata chunkMetadata) {
        return Optional.of(chunkMetadata)
                .map(ChunkMetadata::getTraits)
                .flatMap(this::chunkLocation)
                .map(Location::retrieveUnchecked);
    }

    @Override
    public Stream<Location> allLocations() {
        return StreamSupport.stream(locationRepository.find().spliterator(), false)
                .map(Location.class::cast);
    }

    @Override
    public Chunk encrypt(Chunk chunk, KeyPairPool KeyPairPool) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Chunk decrypt(Chunk encryptedChunk, KeyPairPool KeyPairPool) {
        throw new UnsupportedOperationException();
    }


    private ChunkMetadata newFragment(Chunk chunk) throws IOException {
        Path chunkPath = Paths.get(fragmentPath.toAbsolutePath().toString(), UUID.randomUUID().toString());
        Files.write(chunkPath, chunk.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        NitriteChunkMetadata nitriteChunkMetadata = new NitriteChunkMetadata(
                chunk,
                new FragmentPathStorage(chunkPath)
        );
        metadataRepository.insert(nitriteChunkMetadata);
        locationRepository.insert(new NitriteLocation(nitriteChunkMetadata, chunkPath, 0));
        nitrite.commit();
        return nitriteChunkMetadata;

    }

    private static ObjectFilter ofTraits(Stream<Trait> traits) {
        return ObjectFilters.and(
                traits.map(NitriteDriver::ofTrait)
                        .toArray(ObjectFilter[]::new)
        );
    }

    private static ObjectFilter ofTrait(Trait trait) {
        return ObjectFilters.elemMatch(TRAITS_FIELD, ObjectFilters.and(
                ObjectFilters.eq("name", trait.getName()),
                ObjectFilters.eq("content", trait.getContent())
        ));
    }

}
