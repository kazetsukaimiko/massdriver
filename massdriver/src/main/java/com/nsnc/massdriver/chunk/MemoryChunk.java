package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.CryptUtils;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by luna on 8/1/17.
 * MemoryChunk - A Chunk which is completely loaded into Memory.
 *
 */
public class MemoryChunk extends Chunk {

    public MemoryChunk(ChunkMetadata chunkMetadata, ByteBuffer byteBuffer) {
        super(chunkMetadata, byteBuffer);
    }

    /**
     * Last-minute description generator for speed
     * @return
     */
    @Override
    public List<Trait> getTraits() {
        if (super.getTraits() == null || super.getTraits().isEmpty()) {
            setTraits(CryptUtils.makeDescription(getData()));
        }
        return super.getTraits();
    }

}
