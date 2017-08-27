package com.nsnc.massdriver.nitrite;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.MemoryChunk;
import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.nitrite.entity.NitriteChunk;
import com.nsnc.massdriver.nitrite.entity.NitriteChunkTrait;
import com.nsnc.massdriver.path.UUIDByteRepository;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.WriteResult;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NitriteDriver implements Driver<UUID> {

    private Path basedir;
    private transient Nitrite db;
    private transient UUIDByteRepository uuidByteRepository;
    //private transient ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Path getBasedir() {
        return basedir;
    }

    public void setBasedir(Path basedir) throws IOException {
        this.basedir = Files.createDirectories(basedir);
        try {
            uuidByteRepository = new uuidByteRepository(getRepositoryDirectory());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't create ByteRepository: ", e);
        }
        db = Nitrite.builder()
                .compressed()
                .filePath(Paths.get(basedir.toAbsolutePath().toString(), "index.db").toFile())
                .openOrCreate("mass", "driver");
    }

    public Path getRepositoryDirectory() throws IOException {
        return Files.createDirectories(Paths.get(basedir.toAbsolutePath().toString(), "chunks"));
    }


    private <NT> ObjectRepository<NT> getRepository(Class<NT> klazz) {
        return db.getRepository(klazz);
    }

    <NT> WriteResult persistNitrite(Class<NT> klazz, NT entity) {
        ObjectRepository<NT> repo = getRepository(klazz);
        return repo.insert(entity);
    }

    public NitriteDriver(Path path) throws IOException {
        setBasedir(path);
    }
    public NitriteDriver(String path) throws IOException {
        this(Paths.get(path));
    }

    public NitriteDriver() {

    }


    @Override
    public Asset renameAsset(UUID assetId1, String baseContent1) throws IOException {
        return null;
    }

    @Override
    public UUID persistAsset(Asset asset) throws IOException {
        return null;
    }

    @Override
    public UUID persistAsset(UUID id, Asset asset) throws IOException {
        return null;
    }

    @Override
    public Asset retrieveAsset(UUID assetIdentifier) {
        return null;
    }

    @Override
    public Stream<Asset> findAssets(String assetName, Trait... traits) {
        return null;
    }

    @Override
    public UUID persistChunk(Chunk chunk) {
        return persistChunk(UUID.randomUUID(), chunk);
    }

    @Override
    public UUID persistChunk(UUID id, Chunk chunk) {
        persistData(id, chunk.getData()); // Save the Data
        persistNitrite(NitriteChunk.class, new NitriteChunk(id, chunk.getPosition(), chunk.getLength())); // Data the Chunk Meta
        chunk.getDescription().getTraits().stream() // Save the Traits.
                .map(trait -> new NitriteChunkTrait(null, id, trait))
                .forEach(nitriteChunkTrait -> persistNitrite(NitriteChunkTrait.class, nitriteChunkTrait));
        return id;
    }

    @Override
    public Chunk retrieveChunk(UUID chunkId) {
        try {
            return new MemoryChunk(retrieveData(chunkId), retrieveChunkMetadata(chunkId).getPosition(), retrieveChunkDescrtiption(chunkId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private NitriteChunk retrieveChunkMetadata(UUID chunkId) {
        return getRepository(NitriteChunk.class)
                .find(ObjectFilters.eq("id", chunkId))
                .firstOrDefault();
    }
    private Description retrieveChunkDescrtiption(UUID chunkId) {
        return new Description(
                StreamSupport.stream(
                    getRepository(NitriteChunkTrait.class)
                    .find(ObjectFilters.eq("chunkId", chunkId))
                    .spliterator(),
                    false
                )
                .map(NitriteChunkTrait::getTrait)
                .collect(Collectors.toList())
        );

    }

    @Override
    public UUID persistData(byte[] bytes) {
        return uuidByteRepository.persistData(bytes);
    }

    @Override
    public UUID persistData(UUID id, byte[] bytes) {
        return uuidByteRepository.persistData(id, bytes);
    }

    @Override
    public UUID stream(Stream<? extends ByteBuffer> byteBufferStream) throws IOException {
        return uuidByteRepository.stream(byteBufferStream);
    }

    @Override
    public UUID stream(UUID id, Stream<? extends ByteBuffer> byteBufferStream) throws IOException {
        return uuidByteRepository.stream(id, byteBufferStream);
    }

    @Override
    public byte[] retrieveData(UUID id) throws IOException {
        return uuidByteRepository.retrieveData(id);
    }

    @Override
    public Stream<ByteBuffer> stream(UUID id, int chunkSize) throws IOException {
        return uuidByteRepository.stream(id, chunkSize);
    }
}