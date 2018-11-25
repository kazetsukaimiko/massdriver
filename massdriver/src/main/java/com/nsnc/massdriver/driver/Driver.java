package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.AssetSink;
import com.nsnc.massdriver.asset.AssetSource;
import com.nsnc.massdriver.chunk.ChunkSink;
import com.nsnc.massdriver.chunk.ChunkSource;
import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.chunk.UnresolvableChunkException;
import com.nsnc.massdriver.crypt.KeyPairPool;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public interface Driver extends AssetSink, AssetSource, ChunkSink, ChunkSource { //, Driver {

    /*
    Asset publish(Index index) throws IOException;
    default Asset publish(Path path) throws IOException {
        return publish(saveIndex(path));
    }

    Index unpublish(Asset asset);
    */

    Asset encrypt(Asset asset, KeyPairPool KeyPairPool);
    Asset descrypt(Asset asset, KeyPairPool KeyPairPool);

    default Path writeToPath(List<Trait> traits, Path target) throws IOException {
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
}
