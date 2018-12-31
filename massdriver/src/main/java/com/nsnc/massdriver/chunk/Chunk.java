package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.CryptUtils;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by luna on 8/1/17.
 * Chunk Interface -
 * The pairing of a data fragment with an identifier.
 *
 */
public class Chunk extends ChunkMetadata {
    /**
     * How we currently decide the default size of chunks.
     * Note that this is the DEFAULT chunk size- chunks, particularly when
     * written to, can change size!
     */
    public static final int DEFAULT_CHUNK_SIZE = 1024 * 1024; // 1MB

    protected ByteBuffer data;

    public Chunk(ChunkMetadata chunkMetadata, ByteBuffer byteBuffer) {
        super(chunkMetadata);
        data = byteBuffer;
    }

    public Chunk(Chunk chunk) {
        this(chunk, chunk.getData());
    }

    public Chunk() {
    }

    public ByteBuffer getData() {
        return cloneByteBuffer(data);
    }

    public static ByteBuffer cloneByteBuffer(final ByteBuffer original) {
        ByteBuffer clone = ByteBuffer.allocate(original.capacity());
        original.rewind();
        clone.put(original);
        original.rewind();
        clone.flip();
        return clone;
    }

    @Override
    public int getLength() {
        return toByteArray().length;
    }

    @Override
    public void setLength(int length) {
        throw new UnsupportedOperationException("Cannot change data length without changing data");
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public byte[] toByteArray() {
        return data != null ? data.array() : new byte[0];
    }

    public final boolean verify() {
        return CryptUtils.makeDescription(getData()).stream()
                .filter(trait -> getTraits().stream().noneMatch(measured -> measured.fails(trait)))
                .anyMatch(trait -> getTraits().stream().anyMatch(measured -> measured.equals(trait)));
    }

}
