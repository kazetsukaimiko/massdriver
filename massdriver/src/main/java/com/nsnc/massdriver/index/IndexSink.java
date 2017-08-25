package com.nsnc.massdriver.index;

import java.io.IOException;
import java.nio.file.Path;

public interface IndexSink<AT extends Index> {
    Path persistIndex(AT index) throws IOException;
}

