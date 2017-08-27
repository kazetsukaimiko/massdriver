package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.data.ByteSink;

public interface ChunkSink<CI, CT extends Chunk> extends ByteSink<CI> {
    CI persistChunk(Chunk chunk);
    CI persistChunk(CI chunkId, Chunk chunk);
}
