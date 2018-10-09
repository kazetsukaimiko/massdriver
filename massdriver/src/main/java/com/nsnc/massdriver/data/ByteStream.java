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

/**
 * Base layer utility class to Stream in and out of files.
 */
public class ByteStream {
    public static Stream<ByteBuffer> stream(Path path, int chunksize) throws IOException {
        final long size = Files.size(path);
        final long limit = (long) Math.ceil((double) size / (double) chunksize);
        final SeekableByteChannel seekableByteChannel = Files.newByteChannel(path);

        Supplier<ByteBuffer> bbsupplier = new Supplier<ByteBuffer>() {
            long remaining = size;
            @Override
            public ByteBuffer get() {
                int allocSize = (remaining > chunksize)? chunksize : (int) remaining;
                remaining = remaining - allocSize;
                ByteBuffer byteBuffer = ByteBuffer.allocate(allocSize);
                try {
                    System.out.println(seekableByteChannel.position() + ":" + remaining);
                    seekableByteChannel.read(byteBuffer);
                    //seekableByteChannel.position(seekableByteChannel.position()+chunksize);
                    System.out.println("MD5:"+CryptUtils.hash("MD5", byteBuffer.array()));
                } catch (IOException e) {
                    throw new RuntimeException("Can't read to stream", e);
                }

                return byteBuffer;
            }
        };
        return Stream.generate(bbsupplier).limit(limit);
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
