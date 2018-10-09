package com.nsnc.massdriver.asset;

import com.nsnc.massdriver.Description;

import java.util.Objects;

/**
 * Created by luna on 8/8/17.
 */
public abstract class BaseAsset implements Asset {
    private String name;
    private long size;
    private Description description;
    private String contentType;
    private String urn;

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
    public Description getDescription() {
        return description;
    }
    public void setDescription(Description description) {
        this.description = description;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset that = (Asset) o;
        return Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
    }

    @Override
    public String toString() {
        return "BaseAsset{" +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", description=" + description +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
