package com.nsnc.massdriver.chunk;

public interface ChunkSource<AI, AT extends Chunk> {
    AT retrieveChunk(AI ChunkIdentifier);
}
