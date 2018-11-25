package com.nsnc.massdriver.nitrite.entity;

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.BaseAsset;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.io.IOException;

/*
 * A record of an asset we know of.
 */
public class NitriteAsset extends BaseAsset implements Asset {
    @Id
    private NitriteId nitriteId;

    public NitriteAsset() {
    }

    public NitriteAsset(Asset asset) throws IOException {
        super(asset);
    }

    public NitriteId getNitriteId() {
        return nitriteId;
    }

    public void setNitriteId(NitriteId nitriteId) {
        this.nitriteId = nitriteId;
    }
}
