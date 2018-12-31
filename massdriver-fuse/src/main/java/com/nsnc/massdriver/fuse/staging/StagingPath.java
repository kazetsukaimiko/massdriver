package com.nsnc.massdriver.fuse.staging;

import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseContext;

public abstract class StagingPath {
    private final FuseContext context;
    private String name;
    private StagingDirectory parent;

    public FuseContext getContext() {
        return context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StagingDirectory getParent() {
        return parent;
    }

    public void setParent(StagingDirectory parent) {
        this.parent = parent;
    }

    public StagingPath(FuseContext context, String name) {
        this.context = context;
        this.name = name;
    }

    public StagingPath(FuseContext context, String name, StagingDirectory parent) {
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


    /*
    ////////////dddddd
    dddddd -> null

    ////////
    -> this
     */


    protected StagingPath find(String path) {
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
