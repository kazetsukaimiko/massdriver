package com.nsnc.massdriver.chunk;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Sequence;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.data.ByteRepository;
import com.nsnc.massdriver.data.ByteStream;

public class ChunkUtils {

    protected static final Logger logger = Logger.getLogger(ChunkUtils.class.getName());

    public static Path writeChunkStream(Path filePath, Stream<? extends Chunk> chunkStream) throws IOException {
        ByteStream.stream(filePath, chunkStream.map(Chunk::getByteBuffer));
        return filePath;
    }

    public static String hashChunkStream(String algorithm, Stream<? extends Chunk> chunkStream) {
        Optional<MessageDigest> digest = CryptUtils.getDigest(algorithm);
        if (digest.isPresent()) {
            MessageDigest messageDigest = digest.get();
            chunkStream
                    .forEach(chunk -> {
                        messageDigest.update(chunk.getData());
                    });
            return CryptUtils.toHexString(messageDigest.digest());
        }
        return null;
    }

    public static byte[] readFileChunk(Path path, long start, long length) {
        try {
            byte[] buffer = new byte[(int) length];
            FileInputStream fis = new FileInputStream(path.toFile());
            fis.skip(start);
            fis.read(buffer);
            return buffer;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read file chunk", e);
        }
    }

    public static Stream<MemoryChunk> chunkStream(Path aPath) throws IOException {
        final int[] position = {0};
        return ByteStream.stream(aPath, Chunk.DEFAULT_CHUNK_SIZE)
                .map(byteBuffer -> {
                    MemoryChunk chunk = new MemoryChunk(byteBuffer.array(), position[0]);
                    position[0] += chunk.getLength();
                    return chunk;
                });
    }

    public static Stream<Sequence> sequenceStream(Path aPath) throws IOException {
        final List<Long> positions = new ArrayList<>();
        List<Description> descriptions = chunkStream(aPath)
                .map(memchunk -> {
                            positions.add(memchunk.getPosition());
                            return memchunk.getDescription();
                })
                .collect(Collectors.toList());
        return IntStream.range(0, descriptions.size())
                .mapToObj(i -> new Sequence(
                        positions.get(i),
                        i > 0 ? descriptions.get(i-1) : null,
                        descriptions.get(i),
                        i < descriptions.size() ? descriptions.get(i+1) : null
                ));
    }


}
