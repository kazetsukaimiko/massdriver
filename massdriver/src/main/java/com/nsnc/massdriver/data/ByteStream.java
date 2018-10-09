package com.nsnc.massdriver.data;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.crypt.CryptUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Base layer utility class to Stream in and out of files.
 */
public class ByteStream {
    public static Stream<ByteBuffer> stream(Path path, int chunksize) throws IOException {
        return StreamSupport.stream(
                new ByteBufferIterable(path, chunksize).spliterator(),
                false
        );
    }

    public static Path stream(Path path, Stream<? extends ByteBuffer> byteBufferStream) throws IOException {
        try (OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            byteBufferStream.forEach(byteBuffer -> {
                try {
                    os.write(byteBuffer.array());
                } catch (IOException e) {
                    throw new RuntimeException("Couldn't write stream", e);
                }
            });
        }
        return path;
    }
}
