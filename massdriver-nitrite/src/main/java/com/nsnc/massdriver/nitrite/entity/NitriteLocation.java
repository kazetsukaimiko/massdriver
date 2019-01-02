package com.nsnc.massdriver.nitrite.entity;

import com.nsnc.massdriver.chunk.ChunkMetadata;
import com.nsnc.massdriver.chunk.Location;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.nio.file.Path;

public class NitriteLocation extends Location {

    @Id
    private NitriteId nitriteId;

    public NitriteLocation() {
    }

    public NitriteLocation(Location location) {
        super(location);
    }

    public NitriteLocation(ChunkMetadata chunkMetadata, Path path, int position) {
        super(chunkMetadata, path, position);
    }

    public NitriteLocation(ChunkMetadata chunkMetadata, Path path) {
        super(chunkMetadata, path);
    }

    public NitriteId getNitriteId() {
        return nitriteId;
    }

    public void setNitriteId(NitriteId nitriteId) {
        this.nitriteId = nitriteId;
    }
}
