package com.nsnc.massdriver.chunk;

public interface ChunkRepository<AI, AT extends Chunk> extends ChunkSink<AI, AT>, ChunkSource<AI, AT> {
}
