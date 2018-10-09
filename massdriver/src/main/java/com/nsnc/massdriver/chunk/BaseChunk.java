package com.nsnc.massdriver.chunk;

import com.jcabi.urn.URN;
import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by luna on 8/8/17.
 */
public abstract class BaseChunk implements Chunk {
    protected ByteBuffer data;
    protected long position;
    protected Description description;
    private List<String> encodings;

    @Override
    public ByteBuffer getData() {
        if (data.position() > 0) {
            data = data.rewind();
        }
        return data;
    }

    @Override
    public void setData(ByteBuffer data) {
        this.data = data;
    }

    @Override
    public byte[] toByteArray() {
        return data != null ? data.array() : new byte[0];
    }

    @Override
    public long getPosition() {
        return position;
    }

    @Override
    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public List<String> getEncodings() {
        return encodings;
    }

    public void setEncodings(List<String> encodings) {
        this.encodings = encodings;
    }

    @Override
    public int getLength() {
        return (data != null) ? data.capacity() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return // Arrays.equals(data, chunk.getData()) &&
                Objects.equals(getDescription(), chunk.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }
}
