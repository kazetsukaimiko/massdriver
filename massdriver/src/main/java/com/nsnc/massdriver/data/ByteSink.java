package com.nsnc.massdriver.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.Stream;

public interface ByteSink<BI> {
    BI persistData(byte[] bytes);
    BI persistData(BI id, byte[] bytes);

    BI stream(Stream<? extends ByteBuffer> byteBufferStream) throws IOException;
    BI stream(BI id, Stream<? extends ByteBuffer> byteBufferStream) throws IOException;
}
