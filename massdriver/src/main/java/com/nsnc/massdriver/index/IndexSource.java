package com.nsnc.massdriver.index;

import java.nio.file.Path;

public interface IndexSource<AT extends Index> {
    AT retrieveIndex(Path indexIdentifier);
}

