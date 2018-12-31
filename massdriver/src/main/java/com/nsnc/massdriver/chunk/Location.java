package com.nsnc.massdriver.chunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class that points to exactly on the filesystem where a Chunk's data can be found.
 */
public class Location extends ChunkMetadata {
    private Path path;
    private long storagePosition;

    public Location(ChunkMetadata chunkMetadata, Path path, long storagePosition) {
        super(chunkMetadata);
        this.path = path;
        this.storagePosition = storagePosition;
    }

    public Location(ChunkMetadata chunkMetadata, Path path) {
        this(chunkMetadata, path, chunkMetadata.getStoragePosition());
    }

    public Location(Location location) {
        this(location, location.getPath(), location.getStoragePosition());
    }

    public Location() {
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public long getStoragePosition() {
        return storagePosition;
    }

    public void setStoragePosition(long storagePosition) {
        this.storagePosition = storagePosition;
    }

    public MemoryChunk retrieve() throws IOException {
        SeekableByteChannel seekableByteChannel = Files
                .newByteChannel(getPath());
        ByteBuffer byteBuffer = ByteBuffer.allocate(getLength());
        seekableByteChannel.position(getStoragePosition());
        seekableByteChannel.read(byteBuffer);
        byteBuffer.rewind();
        return new MemoryChunk(this, byteBuffer);
    }

    public MemoryChunk retrieveUnchecked() {
        try {
            return retrieve();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
