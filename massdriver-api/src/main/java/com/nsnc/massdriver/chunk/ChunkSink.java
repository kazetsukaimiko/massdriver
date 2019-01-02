package com.nsnc.massdriver.chunk;

import java.io.IOException;

public interface ChunkSink {
    ChunkMetadata persistChunk(Chunk chunk) throws IOException;
}
