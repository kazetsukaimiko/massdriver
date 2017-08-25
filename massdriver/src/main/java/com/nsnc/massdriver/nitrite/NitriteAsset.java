package com.nsnc.massdriver.nitrite;

import com.jcabi.urn.URN;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.BaseAsset;
import com.nsnc.massdriver.Description;
import org.dizitart.no2.objects.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Created by luna on 8/8/17.
 */
public class NitriteAsset extends BaseAsset {

    @Id
    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID uuid) {
        id = uuid;
    }

    private Description description;
    public Description getDescription() {
        return description;
    }
    public void setDescription(Description description) {
        this.description = description;
    }

    private  List<Description> chunkInfo = new ArrayList<>();
    @Override
    public List<Description> getChunkInfo() {
        return chunkInfo;
    }
    public void setChunkInfo(List<Description> chunkInfo) {
        this.chunkInfo = chunkInfo;
    }

    @Override
    public Stream<NitriteChunk> stream() {
        return nitriteDriver.streamChunks(getId());
    }

    private transient NitriteDriver nitriteDriver;
    public NitriteDriver getNitriteDriver() { return nitriteDriver; }
    public void setNitriteDriver(NitriteDriver nitriteDriver) {
        this.nitriteDriver = nitriteDriver;
    }

    public NitriteAsset resume(NitriteDriver nitriteDriver) {
        this.nitriteDriver = nitriteDriver; return this;
    }

    public NitriteAsset(NitriteDriver nitriteDriver, Asset asset) {
        this(nitriteDriver);
        setContentType(asset.getContentType());
        setDescription(asset.getDescription());
        setSize(asset.getSize());
        setName(asset.getName());
        setChunkInfo(asset.getChunkInfo());
    }
    public NitriteAsset(NitriteDriver nitriteDriver) {
        setNitriteDriver(nitriteDriver);
    }

    public NitriteAsset() {

    }
}
