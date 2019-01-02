package com.nsnc.massdriver.chunk;

@FunctionalInterface
public interface ParentChunkSupplier {
    Chunk get() throws IndecipherableChunkException;

    static ParentChunkSupplier unsupported(Chunk chunk) {
        return () -> { throw new IndecipherableChunkException(
                chunk, "No parent supplier"
        ); };
    }
}

