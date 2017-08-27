package com.nsnc.massdriver.nitrite.deprecated;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.Description;
import org.dizitart.no2.objects.Id;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

public class NitriteChunk implements Chunk {
    @Id
    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID uuid) {
        this.id = uuid;
    }

    private UUID assetId;
    public UUID getAssetId() {
        return assetId;
    }
    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }
    public NitriteChunk assetId(UUID assetId) {
        this.assetId = assetId; return this;
    }

    private transient NitriteDriver nitriteDriver;
    private transient byte[] data;

    private long position = 0;



    @Override
    public byte[] getData() {
        if (data == null) {
            data = load();
        }   return data;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return (getData()!=null)? ByteBuffer.allocate(getData().length).put(getData()) : null;
    }

    public NitriteDriver getNitriteDriver() {
        return nitriteDriver;
    }

    public void setNitriteDriver(NitriteDriver nitriteDriver) {
        this.nitriteDriver = nitriteDriver;
    }

    public NitriteChunk resume(NitriteDriver nitriteDriver) {
        setNitriteDriver(nitriteDriver); return this;
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
    public int getLength() {
        return (getData()!=null) ? getData().length : 0;
    }

    private Description description;

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public NitriteChunk(NitriteDriver nitriteDriver, UUID id) {
        this.nitriteDriver = nitriteDriver;
        setId(id);
    }

    public NitriteChunk(NitriteDriver nitriteDriver, Chunk chunk) {
        this.nitriteDriver = nitriteDriver;
        setDescription(chunk.getDescription());
        setPosition(chunk.getPosition());
        setData(chunk.getData());
    }

    public NitriteChunk() {

    }

    public byte[] load()  {
        if (nitriteDriver != null) {
            try {
                return nitriteDriver.retrieveData(getId());
            } catch (IOException e) {
                throw new RuntimeException("Couldn't retrieve data.", e);
            }
        }
        throw new IllegalStateException("No backing nitrite driver! You must NitriteChunk.resume(NitriteDriver driver) before loading!");
    }

    public UUID save() {
        if (getId() == null) {
            setId(UUID.randomUUID());
        }
        nitriteDriver.persistNitrite(NitriteChunk.class, this);
        return nitriteDriver.persistData(getId(), getData());
    }
}
