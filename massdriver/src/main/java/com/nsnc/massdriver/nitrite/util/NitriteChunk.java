package com.nsnc.massdriver.nitrite.util;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkDelegate;
import com.nsnc.massdriver.chunk.MemoryChunk;
import com.nsnc.massdriver.chunk.storage.PathStorage;
import com.nsnc.massdriver.nitrite.entity.NitriteChunkMetadata;

import java.io.IOException;
import java.util.logging.Logger;

public class NitriteChunk extends ChunkDelegate {

    private static final Logger LOGGER = Logger.getLogger(NitriteChunk.class.getName());

    private final NitriteChunkMetadata nitriteChunkMetadata;
    private final PathStorage pathStorage;

    public NitriteChunk(NitriteChunkMetadata nitriteChunkMetadata, PathStorage pathStorage) {
        this.nitriteChunkMetadata = nitriteChunkMetadata;
        this.pathStorage = pathStorage;
    }

    private Chunk delegate;

    @Override
    public Chunk loadChunk() {
        try {
            return new MemoryChunk(
                    nitriteChunkMetadata,
                    pathStorage.retrieve(nitriteChunkMetadata).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
