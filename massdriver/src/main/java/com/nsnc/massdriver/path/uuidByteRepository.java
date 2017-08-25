package com.nsnc.massdriver.path;

import com.nsnc.massdriver.data.ByteRepository;
import com.nsnc.massdriver.data.ByteStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Simple ByteRepository that stores its data under a directory with a UUID naming convention.
 *
 */
public class uuidByteRepository implements ByteRepository<UUID> {

    private Path basedir;

    public Path getBasedir() {
        return basedir;
    }

    public void setBasedir(Path basedir) {
        this.basedir = basedir;
    }

    @Override
    public UUID persistData(byte[] bytes) {
        return persistData(UUID.randomUUID(), bytes);
    }

    @Override
    public UUID persistData(UUID id, byte[] bytes) {
        try {
            Files.write(toPath(id), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't persist: ", e);
        }
        return id;
    }

    @Override
    public UUID stream(Stream<? extends ByteBuffer> byteBufferStream) throws IOException {
        return stream(UUID.randomUUID(), byteBufferStream);
    }

    @Override
    public UUID stream(UUID id, Stream<? extends ByteBuffer> byteBufferStream) throws IOException {
        if (id == null) {
            id = UUID.randomUUID();
        }
        ByteStream.stream(toPath(id), byteBufferStream);
        return id;
    }

    @Override
    public byte[] retrieveData(UUID identifier) {
        try {
            return Files.readAllBytes(toPath(identifier));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't persist: ", e);
        }
    }

    @Override
    public Stream<ByteBuffer> stream(UUID id, int chunkSize) throws IOException {
        return ByteStream.stream(toPath(id), chunkSize);
    }

    public Path toPath(UUID id) {
        return Paths.get(basedir.toAbsolutePath().toString(), id.toString());
    }

    public uuidByteRepository(Path basedir) throws IOException {
        setBasedir(basedir);
        Files.createDirectories(basedir);
    }

    public uuidByteRepository() {

    }
}
