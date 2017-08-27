package com.nsnc.massdriver.nitrite.entity;

import org.dizitart.no2.objects.Id;

import java.util.UUID;

public class NitriteAsset {
    @Id
    private UUID id;
    private String name;
    private String contentType;
    private long size;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public NitriteAsset(UUID id, String name, String contentType, long size) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }

    public NitriteAsset() {
    }
}
