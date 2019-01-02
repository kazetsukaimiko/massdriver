package com.nsnc.massdriver.chunk;

public class IndecipherableChunkException extends Exception {

    public IndecipherableChunkException(Chunk chunk, String message) {
        super(String.format("Chunk %s parent is indecipherable: %s",
                chunk.getTraits(),
                message
                ));
    }

    public IndecipherableChunkException(Chunk chunk, Throwable cause) {
        this(chunk, cause.getMessage());
    }

}
