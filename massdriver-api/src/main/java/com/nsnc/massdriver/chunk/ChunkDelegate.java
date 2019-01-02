package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Trait;

import java.nio.ByteBuffer;
import java.util.List;

public abstract class ChunkDelegate extends Chunk {

    public abstract Chunk loadChunk();

    @Override
    public ByteBuffer getData() {
        return loadChunk().getData();
    }

    @Override
    public void setData(ByteBuffer data) {
        loadChunk().setData(data);
    }

    @Override
    public byte[] toByteArray() {
        return loadChunk().toByteArray();
    }

    @Override
    public long getStoragePosition() {
        return loadChunk().getStoragePosition();
    }

    @Override
    public void setStoragePosition(long storagePosition) {
        loadChunk().setStoragePosition(storagePosition);
    }

    @Override
    public int getLength() {
        return loadChunk().getLength();
    }

    @Override
    public void setLength(int length) {
        loadChunk().setLength(length);
    }

    @Override
    public List<Trait> getTraits() {
        return loadChunk().getTraits();
    }

    @Override
    public void setTraits(List<Trait> traits) {
        loadChunk().setTraits(traits);
    }

}
