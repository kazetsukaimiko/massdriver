package com.nsnc.massdriver.chunk;

import com.jcabi.urn.URN;
import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;

/**
 * Created by luna on 8/8/17.
 */
public abstract class BaseChunk implements Chunk {
    protected byte[] data;
    protected long position;
    protected Description description;
    private List<String> encodings;

    @Override
    public byte[] getData() {
        return data;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return (getData()!=null)? ByteBuffer.allocate(getData().length).put(getData()) : null;
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
        return (data != null) ? data.length : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chunk chunk = (Chunk) o;
        return //Arrays.equals(data, chunk.data) &&
                Objects.equals(getDescription(), chunk.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }
}
