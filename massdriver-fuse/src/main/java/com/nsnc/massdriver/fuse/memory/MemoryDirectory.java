package com.nsnc.massdriver.fuse.memory;

import jnr.ffi.Pointer;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseContext;

import java.util.ArrayList;
import java.util.List;

public class MemoryDirectory extends MemoryPath {
    private List<MemoryPath> contents = new ArrayList<>();

    public MemoryDirectory(FuseContext context, String name) {
        super(context, name);
    }

    private MemoryDirectory(FuseContext context, String name, MemoryDirectory parent) {
        super(context, name, parent);
    }

    public synchronized void add(MemoryPath p) {
        contents.add(p);
        p.setParent(this);
    }

    public synchronized void deleteChild(MemoryPath child) {
        contents.remove(child);
    }

    @Override
    protected MemoryPath find(String path) {
        if (super.find(path) != null) {
            return super.find(path);
        }
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        synchronized (this) {
            if (!path.contains("/")) {
                for (MemoryPath p : contents) {
                    if (p.getName().equals(path)) {
                        return p;
                    }
                }
                return null;
            }
            String nextName = path.substring(0, path.indexOf("/"));
            String rest = path.substring(path.indexOf("/"));
            for (MemoryPath p : contents) {
                if (p.getName().equals(nextName)) {
                    return p.find(rest);
                }
            }
        }
        return null;
    }

    @Override
    protected void getattr(FileStat stat) {
        stat.st_mode.set(FileStat.S_IFDIR | 0777);
        stat.st_uid.set(getContext().uid.get());
        stat.st_gid.set(getContext().gid.get());
    }

    public synchronized void mkdir(String lastComponent) {
        contents.add(new MemoryDirectory(getContext(), lastComponent, this));
    }

    public synchronized void mkfile(String lastComponent) {
        contents.add(new MemoryFile(getContext(), lastComponent, this));
    }

    public synchronized void read(Pointer buf, FuseFillDir filler) {
        for (MemoryPath p : contents) {
            filler.apply(buf, p.getName(), null, 0);
        }
    }
}