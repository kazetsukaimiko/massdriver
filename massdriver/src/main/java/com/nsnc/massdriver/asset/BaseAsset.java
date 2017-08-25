package com.nsnc.massdriver.asset;

import com.jcabi.urn.URN;
import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;

import java.util.Objects;

/**
 * Created by luna on 8/8/17.
 */
public abstract class BaseAsset implements Asset {

    private String name;
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private long size;
    @Override
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    private Description description;
    @Override
    public Description getDescription() {
        return description;
    }
    public void setDescription(Description description) {
        this.description = description;
    }

    private String contentType;
    @Override
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    @Override
    public String getUrn() {
        if (getDescription() != null) {
            URN urn = new URN("massdriver","asset")
                    .param("xt", getName())
                    .param("contentType", getContentType());
            for (Trait trait : getDescription().getTraits()) {
                urn = urn.param(String.valueOf(trait.getName()).replaceAll("-", "_"), trait.getContent());
            }
            return urn.toString();
        } return null;
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
}
