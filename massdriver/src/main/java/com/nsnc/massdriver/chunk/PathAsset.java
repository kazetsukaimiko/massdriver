package com.nsnc.massdriver.chunk;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.BaseAsset;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.data.ByteStream;
import com.nsnc.massdriver.index.Index;
import org.dizitart.no2.objects.Id;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luna on 8/2/17.
 */
public class PathAsset extends BaseAsset implements Index {

    @Id
    private String pathString;

    List<Description> chunkInfo = new ArrayList<>();

    private Path path;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
        this.pathString = (path != null)? path.toAbsolutePath().toString() : null;
        load();
    }

    @Override
    public List<Description> getChunkInfo() {
        return chunkInfo;
    }

    public void setChunkInfo(List<Description> chunkInfo) {
        this.chunkInfo = chunkInfo;
    }

    private PathAsset load(Consumer<Chunk> consumer) {
        System.out.println("Loading "+path.toAbsolutePath().toString());
        try {
            // Get the size
            setSize(Files.size(path));

            // Make my Description.
            final List<MessageDigest> digests = CryptUtils.getDigests();

            System.out.println("Digests: "
                    + digests.stream().map(digest -> digest.getAlgorithm()).collect(Collectors.joining(", "))
            );

            stream()
                    .forEach(chunk -> {
                        // Make parent Description..
                        ByteBuffer bb = ByteBuffer.allocate(chunk.getData().length);
                        bb.put(chunk.getData());
                        digests.forEach(messageDigest -> messageDigest.update(bb));

                        // Add Chunk Information.
                        chunkInfo.add(chunk.getDescription());

                        // Satiate our consumer method.
                        consumer.accept(chunk);
                    });

            setDescription(new Description(
                    digests.stream().map(Trait::new).collect(Collectors.toList())
            ));
            getDescription().getTraits().forEach(System.out::println);

        } catch (IOException ioe) {
            throw new RuntimeException("Couldn't load.", ioe);
        }
        return this;
    }

    private PathAsset load() {
        return load(chunk -> {});
    }

    // Convenience for reading files...
    // MemoryChunkSupplier should be fastest as it multithreads hashes.
    public PathAsset(Path path) throws IOException {
        setName(path.getFileName().toString());
        setPath(path);
    }


    public PathAsset() {
    }

    @Override
    public Stream<MemoryChunk> stream() throws IOException {
        final int[] position = {0};
        return ByteStream.stream(getPath(), Chunk.DEFAULT_CHUNK_SIZE)
                .map(byteBuffer -> {
                    MemoryChunk chunk = new MemoryChunk(byteBuffer.array(), position[0]);
                    position[0] += chunk.getLength();
                    return chunk;
                });
    }

    @Override
    public Path getId() {
        return getPath();
    }

    @Override
    public void setId(Path id) {
        setPath(id);
    }

    @Override
    public Path fromString(String input) {
        return Paths.get(input);
    }

    @Override
    public String toString() {
        return getPath() != null ? getPath().toAbsolutePath().toString() : null;
    }
}
