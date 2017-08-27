package com.nsnc.massdriver.nitrite.entity;

import com.nsnc.massdriver.Trait;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.util.UUID;

public class NitriteAssetTrait {
    @Id
    private NitriteId id;
    private UUID assetId;
    private Trait trait;

    public NitriteId getId() {
        return id;
    }

    public void setId(NitriteId id) {
        this.id = id;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public Trait getTrait() {
        return trait;
    }

    public void setTrait(Trait trait) {
        this.trait = trait;
    }

    public NitriteAssetTrait(NitriteId id, Trait trait) {
        this.id = id;
        this.trait = trait;
    }

    public NitriteAssetTrait(Trait trait) {
        this.trait = trait;
    }

    public NitriteAssetTrait() {
    }
}
