package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Description;

import java.nio.ByteBuffer;

/**
 * Created by luna on 8/1/17.
 * Chunk Interface -
 * The pairing of a data fragment with an identifier.
 *
 */
public interface Chunk {

    /**
     * Returns the data fragment from this chunk.
     * @return Byte array of the chunk data.
     */
    public byte[] getData();
    public void setData(byte[] data);

    /**
     * Same, but as a ByteBuffer.
     * @return ByteBuffer of the chunk data.
     */
    public ByteBuffer getByteBuffer();

    /**
     * Where this data begins in the Asset
     * @return
     */
    public long getPosition();
    public void setPosition(long position);

    /**
     * A description of this Chunk unique enough to identify it.
     * @return
     */
    public Description getDescription();
    public void setDescription(Description description);

    /**
     * How long this data is.
     * @return
     */
    public int getLength();


    /**
     * How we currently decide the default size of chunks.
     * Note that this is the DEFAULT chunk size- chunks, particularly when
     * written to, can change size!
     */
    public static final int DEFAULT_CHUNK_SIZE = 1024 * 1024; // 1MB
}
