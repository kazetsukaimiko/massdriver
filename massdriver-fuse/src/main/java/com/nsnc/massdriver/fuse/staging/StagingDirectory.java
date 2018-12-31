package com.nsnc.massdriver.fuse.staging;

import jnr.ffi.Pointer;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseContext;

import java.util.ArrayList;
import java.util.List;

public class StagingDirectory extends StagingPath {
    private List<StagingPath> contents = new ArrayList<>();

    public StagingDirectory(FuseContext context, String name) {
        super(context, name);
    }

    private StagingDirectory(FuseContext context, String name, StagingDirectory parent) {
        super(context, name, parent);
    }

    public synchronized void add(StagingPath p) {
        contents.add(p);
        p.setParent(this);
    }

    public synchronized void deleteChild(StagingPath child) {
        contents.remove(child);
    }

    @Override
    protected StagingPath find(String path) {
        if (super.find(path) != null) {
            return super.find(path);
        }
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        synchronized (this) {
            if (!path.contains("/")) {
                for (StagingPath p : contents) {
                    if (p.getName().equals(path)) {
                        return p;
                    }
                }
                return null;
            }
            String nextName = path.substring(0, path.indexOf("/"));
            String rest = path.substring(path.indexOf("/"));
            for (StagingPath p : contents) {
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
        contents.add(new StagingDirectory(getContext(), lastComponent, this));
    }

    public synchronized void mkfile(String lastComponent) {
        contents.add(new StagingFile(getContext(), lastComponent, this));
    }

    public synchronized void read(Pointer buf, FuseFillDir filler) {
        for (StagingPath p : contents) {
            filler.apply(buf, p.getName(), null, 0);
        }
    }
}