package com.nsnc.massdriver.chunk;

public interface ChunkSink<CI, CT extends Chunk> {
    CI persistChunk(Chunk chunk);
    CI persistChunk(CI id, Chunk chunk);
}
