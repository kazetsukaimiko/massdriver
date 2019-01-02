package com.nsnc.massdriver.nitrite.entity;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkMetadata;
import com.nsnc.massdriver.chunk.storage.PathStorage;
import org.dizitart.no2.NitriteId;

import java.io.IOException;

/*
 * When we already have the chunk stored locally.
 */
public class NitriteChunkMetadata extends ChunkMetadata {

    private PathStorage pathStorage;
    public NitriteChunkMetadata() {
    }

    public NitriteChunkMetadata(ChunkMetadata chunkMetadata, PathStorage pathStorage) {
        super(chunkMetadata);
        this.pathStorage = pathStorage;
    }

    public PathStorage getPathStorage() {
        return pathStorage;
    }

    public void setPathStorage(PathStorage pathStorage) {
        this.pathStorage = pathStorage;
    }

    public Chunk resolve() throws IOException {
        return getPathStorage()
                .retrieve(this);
    }
}
