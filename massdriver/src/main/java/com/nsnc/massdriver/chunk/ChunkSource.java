package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.data.ByteSource;

public interface ChunkSource<CI, CT extends Chunk> extends ByteSource<CI> {
    CT retrieveChunk(CI chunkId);
}
