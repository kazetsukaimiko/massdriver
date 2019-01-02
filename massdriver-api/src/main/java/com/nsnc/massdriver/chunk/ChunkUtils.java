package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.data.ByteStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ChunkUtils {

    protected static final Logger LOGGER = Logger.getLogger(ChunkUtils.class.getName());

    public static Path writeChunkStream(Path filePath, Stream<? extends Chunk> chunkStream) throws IOException {
        ByteStream.stream(filePath, chunkStream.map(Chunk::getData));
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

    public static ByteBuffer cloneByteBuffer(final ByteBuffer original) {
        ByteBuffer clone = ByteBuffer.allocate(original.capacity());
        original.rewind();
        clone.put(original);
        original.rewind();
        clone.flip();
        return clone;
    }

    public static Stream<MemoryChunk> chunkStream(Path aPath) throws IOException {
        final int[] position = {0};
        return ByteStream.stream(aPath, Chunk.DEFAULT_CHUNK_SIZE)
                .map(ChunkUtils::cloneByteBuffer)
                .map(byteBuffer -> {
                    final List<Trait> traitList = CryptUtils.makeDescription(byteBuffer);
                    byteBuffer.rewind();
                    final MemoryChunk chunk = new MemoryChunk(new ChunkMetadata(traitList, position[0], byteBuffer.capacity()), byteBuffer);
                    position[0] += chunk.getLength();
                    return chunk;
                });
    }
/*
    public static Stream<Sequence> sequenceStream(Path aPath) throws IOException {
        final List<Long> positions = new ArrayList<>();
        List<Description> descriptions = chunkStream(aPath)
                .map(memchunk -> {
                            positions.add(memchunk.getStoragePosition());
                            return memchunk.getTraits();
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
*/

}
