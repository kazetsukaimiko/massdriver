package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.chunk.ChunkMetadata;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by luna on 8/8/17.
 */
public abstract class BaseAsset implements Asset {
    private String name;
    private long size;
    private List<Trait> traits;
    private String contentType;
    private String urn;
    private List<ChunkMetadata> chunkMetadata;

    public BaseAsset(Asset asset) {
        setName(asset.getName());
        setSize(asset.getSize());
        setTraits(asset.getTraits());
        setContentType(asset.getContentType());
        setUrn(asset.makeUrn(getName(), getContentType(), getTraits()));
        setChunkMetadata(asset.getChunkMetadata());
    }

    protected BaseAsset() {
    }

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }


    @Override
    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    @Override
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    @Override
    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    @Override
    public List<ChunkMetadata> getChunkMetadata() {
        return chunkMetadata;
    }

    public void setChunkMetadata(List<ChunkMetadata> chunkMetadata) {
        this.chunkMetadata = chunkMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset that = (Asset) o;
        return Trait.matches(getTraits(), that.getTraits());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, contentType);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", traits=" + traits +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
