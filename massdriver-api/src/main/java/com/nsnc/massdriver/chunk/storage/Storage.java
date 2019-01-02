package com.nsnc.massdriver.chunk.storage;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkMetadata;

import java.io.IOException;

public interface Storage {

    Chunk retrieve(ChunkMetadata chunkMetadata) throws IOException;
}
