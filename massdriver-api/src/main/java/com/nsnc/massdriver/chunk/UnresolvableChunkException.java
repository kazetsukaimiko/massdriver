package com.nsnc.massdriver.chunk;

import java.io.IOException;

public class UnresolvableChunkException extends RuntimeException {
    public UnresolvableChunkException(ChunkMetadata metadata, IOException ioe) {
        super("Couldn't resolve chunk " + metadata, ioe);
    }
}
