package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.chunk.UnresolvableChunkException;

import java.io.IOException;

public class InterruptedChunkStreamException extends IOException {
    public InterruptedChunkStreamException(String message, UnresolvableChunkException uce) {
        super(message, uce);
    }
}
