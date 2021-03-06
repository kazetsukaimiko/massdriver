package com.nsnc.massdriver.fuse.memory;

import jnr.ffi.Pointer;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseContext;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class MemoryFile extends MemoryPath {
    private ByteBuffer contents = ByteBuffer.allocate(0);

    public MemoryFile(FuseContext context, String name) {
        super(context, name);
    }

    public MemoryFile(FuseContext context, String name, MemoryDirectory parent) {
        super(context, name, parent);
    }

    public MemoryFile(FuseContext context, String name, String text) {
        super(context, name);
        try {
            byte[] contentBytes = text.getBytes("UTF-8");
            contents = ByteBuffer.wrap(contentBytes);
        } catch (UnsupportedEncodingException e) {
            // Not going to happen
        }
    }

    @Override
    protected void getattr(FileStat stat) {
        stat.st_mode.set(FileStat.S_IFREG | 0777);
        stat.st_size.set(contents.capacity());
        stat.st_uid.set(getContext().uid.get());
        stat.st_gid.set(getContext().gid.get());
    }

    public int read(Pointer buffer, long size, long offset) {
        int bytesToRead = (int) Math.min(contents.capacity() - offset, size);
        byte[] bytesRead = new byte[bytesToRead];
        synchronized (this) {
            contents.position((int) offset);
            contents.get(bytesRead, 0, bytesToRead);
            buffer.put(0, bytesRead, 0, bytesToRead);
            contents.position(0); // Rewind
        }
        return bytesToRead;
    }

    public synchronized void truncate(long size) {
        if (size < contents.capacity()) {
            // Need to create a new, smaller buffer
            ByteBuffer newContents = ByteBuffer.allocate((int) size);
            byte[] bytesRead = new byte[(int) size];
            contents.get(bytesRead);
            newContents.put(bytesRead);
            contents = newContents;
        }
    }

    public int write(Pointer buffer, long bufSize, long writeOffset) {
        int maxWriteIndex = (int) (writeOffset + bufSize);
        byte[] bytesToWrite = new byte[(int) bufSize];
        synchronized (this) {
            if (maxWriteIndex > contents.capacity()) {
                // Need to create a new, larger buffer
                ByteBuffer newContents = ByteBuffer.allocate(maxWriteIndex);
                newContents.put(contents);
                contents = newContents;
            }
            buffer.get(0, bytesToWrite, 0, (int) bufSize);
            contents.position((int) writeOffset);
            contents.put(bytesToWrite);
            contents.position(0); // Rewind
        }
        return (int) bufSize;
    }
}
