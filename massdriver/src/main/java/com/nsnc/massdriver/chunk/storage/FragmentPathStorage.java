package com.nsnc.massdriver.chunk.storage;

import java.nio.file.Path;

public class FragmentPathStorage extends PathStorage {

    public FragmentPathStorage(Path path) {
        super(path, Type.FRAGMENT);
    }

}
