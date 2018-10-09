package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Description;

import java.nio.ByteBuffer;
import java.util.List;

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
    ByteBuffer getData();
    void setData(ByteBuffer data);

    /**
     * Same, but as a ByteBuffer.
     * @return ByteBuffer of the chunk data.
     */
    byte[] toByteArray();

    /**
     * Where this data begins in the Asset
     * @return
     */
    long getPosition();
    void setPosition(long position);

    /**
     * How large the chunk is.
     * @return
     */
    int getLength();

    /**
     * A description of this Chunk unique enough to identify it.
     * @return
     */
    Description getDescription();
    void setDescription(Description description);


    /**
     * A List describing how the chunk data is encoded, in reverse order.
     * For instance, you could have:
     * 'gzip', 'encrypt:dsa?sha256=
     * @return
     */
    List<String> getEncodings();


    /**
     * How we currently decide the default size of chunks.
     * Note that this is the DEFAULT chunk size- chunks, particularly when
     * written to, can change size!
     */
    int DEFAULT_CHUNK_SIZE = 1024 * 1024; // 1MB
}
