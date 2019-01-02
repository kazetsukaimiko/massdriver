package com.nsnc.massdriver.chunk.storage;

import java.nio.file.Path;

public class CoherentPathStorage extends PathStorage {

    public CoherentPathStorage(Path path) {
        super(path, Type.COHERENT);
    }



}
