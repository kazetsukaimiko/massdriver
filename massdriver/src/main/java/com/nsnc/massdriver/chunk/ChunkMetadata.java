package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.CryptUtils;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;

public class ChunkMetadata {
    private List<Trait> traits;
    private long position;
    private int length;

    public ChunkMetadata(ChunkMetadata chunkMetadata) {
        this(chunkMetadata.getTraits(), chunkMetadata.getStoragePosition(), chunkMetadata.getLength());
    }

    public ChunkMetadata(List<Trait> traits, long position, int length) {
        this.traits = traits;
        this.position = position;
        this.length = length;
    }

    public ChunkMetadata() {
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    public long getStoragePosition() {
        return position;
    }

    public void setStoragePosition(long storagePosition) {
        this.position = storagePosition;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChunkMetadata that = (ChunkMetadata) o;
        return position == that.getStoragePosition() &&
                length == that.getLength() &&
                Objects.equals(traits, that.getTraits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(traits, position, length);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "traits=" + traits +
                ", position=" + position +
                ", length=" + length +
                '}';
    }
}
