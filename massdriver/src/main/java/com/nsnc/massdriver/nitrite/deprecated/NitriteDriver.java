package com.nsnc.massdriver.nitrite.deprecated;

import com.nsnc.massdriver.chunk.*;
import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.path.UUIDByteRepository;
import org.dizitart.no2.Nitrite;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by luna on 8/8/17.
 */
public class NitriteDriver implements IndexRepository<PathAsset>, AssetRepository<UUID, NitriteAsset>, ChunkRepository<UUID, Chunk>, ByteRepository<UUID> {

    private Path basedir;
    private transient Nitrite db;
    private transient UUIDByteRepository uuidByteRepository;
    private transient ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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

    public NitriteDriver(Path path) throws IOException {
        setBasedir(path);
    }
    public NitriteDriver(String path) throws IOException {
        this(Paths.get(path));
    }

    public NitriteDriver() {

    }


    @Override
    public UUID persistAsset(Asset asset) throws IOException {
        return persistAsset(null, asset);
    }

    public NitriteAsset convertAsset(UUID id, Asset asset) {
        NitriteAsset nitriteAsset;
        if (asset instanceof NitriteAsset) {
            nitriteAsset = (NitriteAsset) asset;
            if (nitriteAsset.getId() != null) {
                id = nitriteAsset.getId();
            }
        } else {
            nitriteAsset = new NitriteAsset(this, asset);
        }
        if (id == null) {
            id = UUID.randomUUID();
            nitriteAsset.setId(id);
        }

        // Save Asset
        persistNitrite(NitriteAsset.class, nitriteAsset);
        return nitriteAsset;
    }

    @Override
    public UUID persistAsset(UUID id, Asset asset) throws IOException {
        // Save asset
        final NitriteAsset nitriteAsset = convertAsset(id, asset);

        // Save Chunks
        asset.stream()
                .map(chunk -> new NitriteChunk(this, chunk).assetId(nitriteAsset.getId()))
                .forEach(NitriteChunk::save);

        commit();
        return nitriteAsset.getId();
    }

    @Override
    public NitriteAsset retrieveAsset(UUID id) {
        NitriteAsset nitriteAsset = getRepository(NitriteAsset.class).find(ObjectFilters.eq("id", id)).firstOrDefault();
        nitriteAsset.setNitriteDriver(this);
        return nitriteAsset;
    }

    public Stream<NitriteAsset> findAssets() {
        Cursor<NitriteAsset> cursor = getRepository(NitriteAsset.class).find();
        return StreamSupport.stream(
                cursor.spliterator()
                ,false
        ).map(nitriteAsset -> nitriteAsset.resume(this));
    }

    @Override
    public Stream<NitriteAsset> findAssets(String assetName, Trait... traits) {
        ObjectRepository<NitriteAsset> repo = getRepository(NitriteAsset.class);
        Cursor<NitriteAsset> cursor  = repo.find(
                (traits != null) ?
                ObjectFilters.and(
                        ObjectFilters.eq("name", assetName),
                        ofTraits(traits)
                )
                        :
                        ObjectFilters.eq("name", assetName)
        );
        return StreamSupport.stream(cursor.spliterator(), false);
    }

    private ObjectFilter ofTraits(Trait... traits) {
        return ofTraits(Arrays.asList(traits));
    }

    private ObjectFilter ofTraits(List<Trait> traits) {
        return ObjectFilters.and(
            traits.stream().map(this::ofTrait).toArray(ObjectFilter[]::new)
        );
    }

    private ObjectFilter ofTrait(Trait trait) {
        return ObjectFilters.or(
                ObjectFilters.not(ObjectFilters.in("description.traits.name", trait.getName())),
                ObjectFilters.in("description.traits", trait)
        );
    }

    @Override
    public UUID persistChunk(Chunk chunk) {
        return persistChunk(UUID.randomUUID(), chunk);
    }

    @Override
    public UUID persistChunk(UUID id, Chunk chunk) {
        return persistData(id, chunk.getData());
    }

    @Override
    public Chunk retrieveChunk(UUID identifier) {
        return null;
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
        return stream(UUID.randomUUID(), byteBufferStream);
    }

    @Override
    public UUID stream(UUID id, Stream<? extends ByteBuffer> byteBufferStream) throws IOException {
        return uuidByteRepository.stream(id, byteBufferStream);
    }

    @Override
    public byte[] retrieveData(UUID identifier) throws IOException {
        return uuidByteRepository.retrieveData(identifier);
    }


    @Override
    public Path persistIndex(PathAsset index) throws IOException {
        persistNitrite(PathAsset.class, index);
        return index.getId();
    }

    @Override
    public PathAsset retrieveIndex(Path indexIdentifier) {
        return getRepository(PathAsset.class).find(ObjectFilters.eq("path", indexIdentifier)).firstOrDefault();
    }

    public Stream<ByteBuffer> stream(UUID id, int chunkSize) throws IOException {
        return uuidByteRepository.stream(id, chunkSize);
    }

    public Stream<NitriteChunk> streamChunks(UUID assetId) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                    getRepository(NitriteChunk.class)
                        .find(ObjectFilters.eq("assetId", assetId))
                            .iterator()
                        , Spliterator.ORDERED), false)
                .map(nitriteChunk -> nitriteChunk.resume(this));
    }



    private <NT> ObjectRepository<NT> getRepository(Class<NT> klazz) {
        return db.getRepository(klazz);
    }

    <NT> WriteResult persistNitrite(Class<NT> klazz, NT entity) {
        ObjectRepository<NT> repo = getRepository(klazz);
        return repo.insert(entity);
    }

    public boolean hasUnsavedChanges() {
        return db.hasUnsavedChanges();
    }

    public void commit() {
        db.commit();
    }

    public void close() {
        db.close();
    }


    @Override
    public NitriteAsset renameAsset(UUID identifier, String newName) throws IOException {
        NitriteAsset nitriteAsset = retrieveAsset(identifier);
        nitriteAsset.setName(newName);
        getRepository(NitriteAsset.class).update(nitriteAsset);
        return nitriteAsset;
    }
}
