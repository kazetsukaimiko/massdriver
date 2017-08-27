package com.nsnc.massdriver.chunk;

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
            description = CryptUtils.makeDescription(data);
        }   return description;
    }

    public MemoryChunk(byte[] data, long position, Description description) {
        setData(data);
        setPosition(position);
        setDescription(description);
    }

    public MemoryChunk(Chunk chunk) {
        setData(chunk.getData());
        setPosition(chunk.getPosition());
        setDescription(chunk.getDescription());
    }

    public MemoryChunk(byte[] data, long position) {
        setData(data);
        setPosition(position);
    }

    public MemoryChunk() {

    }
}
