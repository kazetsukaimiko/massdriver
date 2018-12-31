package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.FileAsset;
import com.nsnc.massdriver.chunk.*;
import com.nsnc.massdriver.crypt.KeyPairPool;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Driver-to-Map Bridge
 */
public class MapAccessDriver implements Driver {
    private static final Logger LOGGER = Logger.getLogger(MapAccessDriver.class.getName());


    private final Set<Asset> assets = new HashSet<>();
    //private final Map<Trait, Asset> assetMap;
    private final Map<Trait, Chunk> chunkMap;
    private final List<Location> locations = new ArrayList<>();


    /**
     * Create a Driver that uses Map access as its interface.
     *
     * @param assetMap Map implementation
     * @param chunkMap Map implementation
     */
    public MapAccessDriver(Map<Trait, Asset> assetMap, Map<Trait, Chunk> chunkMap) {
        //this.assetMap = assetMap;
        this.chunkMap = chunkMap;
    }

    /**
     * Memory-based map
     */
    public MapAccessDriver() {
        this(new HashMap<>(), new HashMap<>());
    }



    private Asset putAsset(List<Trait> traits, Asset asset) {
        assets.add(asset);
        //traits.forEach(trait -> assetMap.put(trait, asset));
        return asset;
    }
    private Chunk putChunk(List<Trait> traits, Chunk chunk) {
        traits.forEach(trait -> chunkMap.put(trait, chunk));
        return chunk;
    }

    @Override
    public List<Trait> persistAsset(Asset asset) throws IOException {
        if (asset != null) {
            putAsset(asset.getTraits(), asset);
            if (asset instanceof FileAsset) {
                /*
                ChunkUtils.chunkStream(((FileAsset) asset).getPath())
                        .peek(System.out::println)
                        .forEach(this::persistChunk);
                        */
                asset.getChunkMetadata().stream()
                        .map(chunkMetadata -> new Location(chunkMetadata, ((FileAsset) asset).getPath()))
                        .forEach(locations::add);
            }
            return asset.getTraits();
        }
        throw new IOException("Can't persist null assets...");
    }

    @Override
    public List<Trait> removeAsset(Asset asset) {
        assets.remove(asset);

        return asset.getTraits();
    }

    @Override
    public Optional<Asset> retrieveAsset(List<Trait>  description) {
        return assets.stream()
                .filter(asset -> Trait.matches(description, asset.getTraits()))
                .findFirst();
    }

    @Override
    public Stream<Asset> findAssetsByTraits(Trait... traits) {
        return assetMap.values().stream()
                .filter(a -> traits.length <= 0 || a.getDescription().getTraits().containsAll(Arrays.asList(traits)));
    }

    @Override
    public Stream<Asset> allAssets() {
        return assets.stream();
    }

    @Override
    public ChunkMetadata persistChunk(final Chunk chunk) {
        chunk.getTraits().forEach(trait -> chunkMap.put(trait, chunk));
        return chunk;
    }

    @Override
    public Optional<Location> chunkLocation(List<Trait> chunkTraits) {
        return locations.stream()
                .filter(location -> location.getTraits().stream().noneMatch(lt ->
                        chunkTraits.stream().anyMatch(lt::fails)
                        ))
                .filter(location -> location.getTraits().stream().anyMatch(lt ->
                        chunkTraits.stream().anyMatch(lt::equals)))
                .findFirst();
    }

    @Override
    public Optional<Chunk> retrieveChunk(ChunkMetadata chunkMetadata) {
        Optional<Location> chunkLocation = chunkLocation(chunkMetadata.getTraits());
        if (chunkLocation.isPresent()) {
            return chunkLocation.map(Location::retrieveUnchecked);
        }
        LOGGER.info(String.valueOf(chunkMetadata));
        return Stream.of(chunkMetadata)
                .map(ChunkMetadata::getTraits)
                .flatMap(List::stream)
                .map(chunkMap::get)
                .findFirst();
    }

    @Override
    public Stream<Location> allLocations() {
        return locations.stream();
    }


    @Override
    public Chunk encrypt(Chunk chunk, KeyPairPool KeyPairPool) {
        return null;
    }

    @Override
    public Chunk decrypt(Chunk encryptedChunk, KeyPairPool KeyPairPool) {
        return null;
    }


    public Asset persist(Path path) throws IOException {
        FileAsset asset = new FileAsset(path);
        persistAsset(asset);

        ChunkUtils.chunkStream(path)
                .peek(System.out::println)
                .forEach(this::persistChunk);
        return asset;
    }


    //@Override
    public Asset encrypt(Asset asset, KeyPairPool KeyPairPool) {
        return null;
    }

    //@Override
    public Asset descrypt(Asset asset, KeyPairPool KeyPairPool) {
        return null;
    }

    /*
    @Override
    public Path writeToPath(List<Trait> traits, Path target) throws IOException {
        Optional<Asset> inDriver = retrieveAsset(traits);
        if (!inDriver.isPresent()) {
            throw new NoSuchAssetException(getClass(), traits);
        }
        Asset a = inDriver.get();
        try {
            ChunkUtils.writeChunkStream(
                    target,
                    a.getChunkMetadata().stream()
                            .map(this::retrieveChunkOrInterrupt)
            );
        } catch (UnresolvableChunkException uce) {
            throw new InterruptedChunkStreamException("Couldn't resolve chunks for write", uce);
        }
        return target;
    }
    */

    /*

    @Override
    public Stream<Chunk> findChunksByTraits(Trait... traits) {
        return chunkMap.values().stream()
                .filter(c -> c.getTraits().containsAll(Arrays.asList(traits)));
    }


    @Override
    public Asset publish(Index index) throws IOException {
        Optional<Asset> existing = Optional.of(index)
                .map(Index::getTraits)
                .flatMap(this::retrieveAsset);
        if (existing.isPresent()) {
            return existing.get();
        }
        Asset a = persist(index.getPath());

        index.setTraits(a.getTraits());
        index.setChunkMetadata(a.getChunkMetadata());
        saveIndex(index);

        return a;
    }



    @Override
    public Index unpublish(Asset asset) {
        return null;
    }

    @Override
    public Index saveIndex(Index index) {
        return null;
    }

    @Override
    public Index saveIndex(Path path) {
        Index existing = findIndex(path);
        if (existing != null) {
            return existing;
        }
        Index index = new Index(path);
        Index parent = findIndex(path.toAbsolutePath().getParent());
        if (parent != null) {
            index.setParentId(parent.getIndexId());
        }

        indices.add(index);
        return index;
    }

    @Override
    public UUID remove(Index index) {
        return indices.remove(index) ? index.getIndexId() : null;
    }

    @Override
    public Index findIndex(Path path) {
        return indices.stream()
                .filter(index -> Objects.equals(index.getPath(), path))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Index findIndex(UUID uuid) {
        return indices.stream()
                .filter(index -> Objects.equals(index.getIndexId(), uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Index findIndexByTraits(List<Trait> assetTraits) {
        return null;
    }

    @Override
    public Index findIndexByChunkTraits(List<Trait> chunkTraits) {
        return null;
    }

    @Override
    public List<Index> findShares() {
        return indices.stream()
                .filter(Index::isShared)
                .collect(Collectors.toList());
    }

    @Override
    public List<Index> addShare(Index index) {
        return indices.stream()
                .peek(index1 -> {
                    if (Objects.equals(index, index1)) {
                        index1.setShared(true);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<Index> removeShare(Index index) {
        return indices.stream()
                .peek(index1 -> {
                    if (Objects.equals(index, index1)) {
                        index1.setShared(false);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<Index> listContents(UUID indexId) {
        return indices.stream()
                .filter(index -> Objects.equals(index.getParentId(), indexId))
                .collect(Collectors.toList());
    }
    */
}
