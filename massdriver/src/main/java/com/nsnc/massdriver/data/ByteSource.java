package com.nsnc.massdriver.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.Stream;

public interface ByteSource<BI> {
    byte[] retrieveData(BI id) throws IOException;

    Stream<ByteBuffer> stream(BI id, int chunkSize) throws IOException;
}
