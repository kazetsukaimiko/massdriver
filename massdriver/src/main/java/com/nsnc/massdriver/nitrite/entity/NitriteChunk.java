package com.nsnc.massdriver.nitrite.entity;

import org.dizitart.no2.objects.Id;

import java.util.UUID;

public class NitriteChunk {
    @Id
    private UUID id;
    private long position;
    private int length;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public NitriteChunk(UUID id, long position, int length) {
        this.id = id;
        this.position = position;
        this.length = length;
    }

    public NitriteChunk() {
    }
}
