package com.nsnc.massdriver.fuse.memory;

import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseContext;

public abstract class MemoryPath {
    private final FuseContext context;
    private String name;
    private MemoryDirectory parent;

    public FuseContext getContext() {
        return context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MemoryDirectory getParent() {
        return parent;
    }

    public void setParent(MemoryDirectory parent) {
        this.parent = parent;
    }

    public MemoryPath(FuseContext context, String name) {
        this.context = context;
        this.name = name;
    }

    public MemoryPath(FuseContext context, String name, MemoryDirectory parent) {
        this.context = context;
        this.name = name;
        this.parent = parent;
    }

    public synchronized void delete() {
        if (parent != null) {
            parent.deleteChild(this);
            parent = null;
        }
    }

    protected MemoryPath find(String path) {
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.equals(name) || path.isEmpty()) {
            return this;
        }
        return null;
    }

    protected abstract void getattr(FileStat stat);

    public void rename(String newName) {
        while (newName.startsWith("/")) {
            newName = newName.substring(1);
        }
        name = newName;
    }
}
