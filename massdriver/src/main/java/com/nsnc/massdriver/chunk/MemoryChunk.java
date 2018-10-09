package com.nsnc.massdriver.chunk;

import java.nio.ByteBuffer;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.crypt.CryptUtils;

/**
 * Created by luna on 8/1/17.
 * MemoryChunk - A Chunk which is completely loaded into Memory.
 *
 */
public class MemoryChunk extends BaseChunk {
    @Override
    public Description getDescription() {
        if (description == null) {
            description = CryptUtils.makeDescription(getData());
        }
        return description;
    }

    public MemoryChunk(ByteBuffer byteBuffer, long position, Description description) {
        setData(byteBuffer);
        setPosition(position);
        setDescription(description);
    }

    public MemoryChunk(Chunk chunk) {
        setData(chunk.getData());
        setPosition(chunk.getPosition());
        setDescription(chunk.getDescription());
    }

    /**
     * Lazily Make the Description
     * @param byteBuffer
     * @param position
     */
    public MemoryChunk(ByteBuffer byteBuffer, long position) {
        setData(byteBuffer);
        setPosition(position);
    }

    public MemoryChunk() {

    }
}
