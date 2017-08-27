package com.nsnc.massdriver.nitrite.entity;

import com.nsnc.massdriver.Trait;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.util.UUID;


public class NitriteChunkTrait {
    @Id
    private NitriteId id;
    private UUID chunkId;
    private Trait trait;

    public NitriteId getId() {
        return id;
    }

    public void setId(NitriteId id) {
        this.id = id;
    }

    public UUID getChunkId() {
        return chunkId;
    }

    public void setChunkId(UUID chunkId) {
        this.chunkId = chunkId;
    }

    public Trait getTrait() {
        return trait;
    }

    public void setTrait(Trait trait) {
        this.trait = trait;
    }

    public NitriteChunkTrait(NitriteId id, UUID chunkId, Trait trait) {
        this.id = id;
        this.chunkId = chunkId;
        this.trait = trait;
    }

    public NitriteChunkTrait(Trait trait) {
        this.trait = trait;
    }

    public NitriteChunkTrait() {
    }
}
