package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.data.ByteSink;

import java.io.IOException;

public interface ChunkSink {
    Description persistChunk(Chunk chunk) throws IOException;
}
