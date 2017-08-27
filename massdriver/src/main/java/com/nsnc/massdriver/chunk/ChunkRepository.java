package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.data.ByteRepository;

public interface ChunkRepository<CI, CT extends Chunk> extends ChunkSink<CI, CT>, ChunkSource<CI, CT>, ByteRepository<CI> {
}
