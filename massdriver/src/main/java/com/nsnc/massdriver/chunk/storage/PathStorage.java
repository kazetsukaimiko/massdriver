package com.nsnc.massdriver.chunk.storage;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkMetadata;
import com.nsnc.massdriver.chunk.MemoryChunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathStorage implements Storage {

    private Path path;
    private Type type = Type.COHERENT;

    PathStorage(Path path, Type type) {
        this.path = path;
        this.type = type;
    }

    public PathStorage() {
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public MemoryChunk retrieveCoherent(ChunkMetadata chunkMetadata) throws IOException {
        SeekableByteChannel seekableByteChannel = Files
                .newByteChannel(getPath())
                .position(chunkMetadata.getStoragePosition());
        ByteBuffer byteBuffer = ByteBuffer.allocate(chunkMetadata.getLength());
        seekableByteChannel.read(byteBuffer);
        byteBuffer.rewind();
        return new MemoryChunk(chunkMetadata, byteBuffer);
    }

    public MemoryChunk retrieveFragment(ChunkMetadata chunkMetadata) throws IOException {
        SeekableByteChannel seekableByteChannel = Files
                .newByteChannel(getPath());
        ByteBuffer byteBuffer = ByteBuffer.allocate(chunkMetadata.getLength());
        seekableByteChannel.read(byteBuffer);
        byteBuffer.rewind();
        return new MemoryChunk(chunkMetadata, byteBuffer);
    }

    @Override
    public Chunk retrieve(ChunkMetadata chunkMetadata) throws IOException {
        return getType().equals(Type.COHERENT) ?
                retrieveCoherent(chunkMetadata)
                :
                retrieveFragment(chunkMetadata);
    }

    enum Type {
        COHERENT,
        FRAGMENT
    }




}
