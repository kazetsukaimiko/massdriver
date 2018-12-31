package com.nsnc.massdriver.fuse;

import com.nsnc.massdriver.driver.Driver;
import jnr.ffi.Pointer;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.FuseStubFS;
import ru.serce.jnrfuse.struct.FuseFileInfo;

public class MassDriverFuse extends FuseStubFS {
    private final Driver driver;

    public MassDriverFuse(Driver driver) {
        this.driver = driver;
    }

    @Override
    public int opendir(String path, FuseFileInfo fi) {
        return super.opendir(path, fi);
    }

    @Override
    public int readdir(String path, Pointer buf, FuseFillDir filter, long offset, FuseFileInfo fi) {
        return super.readdir(path, buf, filter, offset, fi);
    }

    @Override
    public int releasedir(String path, FuseFileInfo fi) {
        return super.releasedir(path, fi);
    }

    @Override
    public int fsyncdir(String path, FuseFileInfo fi) {
        return super.fsyncdir(path, fi);
    }
}
