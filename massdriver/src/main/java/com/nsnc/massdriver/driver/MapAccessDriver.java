package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.FileAsset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.crypt.CryptUtils;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Driver-to-Map Bridge
 */
public class MapAccessDriver implements Driver {

    private final Map<Description, Asset> assetMap;
    private final Map<Description, Chunk> chunkMap;

    /**
     * Create a Driver that uses Map access as its interface.
     *
     * @param assetMap Map implementation
     * @param chunkMap Map implementation
     */
    public MapAccessDriver(Map<Description, Asset> assetMap, Map<Description, Chunk> chunkMap) {
        this.assetMap = assetMap;
        this.chunkMap = chunkMap;
    }

    /**
     * Memory-based map
     */
    public MapAccessDriver() {
        this(new HashMap<>(), new HashMap<>());
    }

    @Override
    public Description persistAsset(Asset asset) throws IOException {
        if (asset != null) {
            if (assetMap.containsKey(asset.getDescription())) {
                System.out.println("DUPLICATE DESCRIPTION!");
                System.out.println(asset.getDescription());
                System.out.println(asset.getDescription().hashCode());
            }
            assetMap.put(asset.getDescription(), asset);
            return asset.getDescription();
        }
        throw new IOException("Can't persist null assets...");
    }

    private Optional<Description> searchDescriptions(Map<Description, ?> inputMap, Description examples) {
        return searchDescriptions(inputMap.keySet().stream(), examples);
    }

    private Optional<Description> searchDescriptions(Stream<Description> comparisons, Description examples) {
        return comparisons
                .filter(comparison -> comparison.getTraits().containsAll(examples.getTraits()))
                .findFirst();
    }

    private <T> Optional<T> searchMap(Map<Description, T> inputMap, Description description) {
        return searchDescriptions(inputMap, description)
                .map(inputMap::get);
    }

    @Override
    public Optional<Asset> retrieveAsset(Description description) {
        return searchMap(assetMap, description);
    }

    @Override
    public Stream<Asset> findAssetsByTraits(Trait... traits) {
        return assetMap.values().stream()
                .filter(a -> traits.length <= 0 || a.getDescription().getTraits().containsAll(Arrays.asList(traits)));
    }

    @Override
    public Description persistChunk(final Chunk chunk) {
        if (chunk != null) {
            if (assetMap.containsKey(chunk.getDescription())) {
                System.out.println("DUPLICATE DESCRIPTION!");
                System.out.println(chunk);
            }
            chunkMap.put(chunk.getDescription(), chunk);
            return chunk.getDescription();
        }
        return null;
    }

    @Override
    public Optional<Chunk> retrieveChunk(Description chunkDescription) {
        return searchMap(chunkMap, chunkDescription);
    }

    @Override
    public Stream<Chunk> findChunksByTraits(Trait... traits) {
        return chunkMap.values().stream()
                .filter(c -> c.getDescription().getTraits().containsAll(Arrays.asList(traits)));
    }

    @Override
    public Asset persist(Path path) throws IOException {
        FileAsset asset = new FileAsset(path);
        persistAsset(asset);

        ChunkUtils.chunkStream(path)
                .peek(System.out::println)
                .forEach(this::persistChunk);
        return asset;
    }
}
