package com.nsnc.massdriver.fuse.staging;


import jnr.ffi.Platform;
import jnr.ffi.Pointer;
import jnr.ffi.types.mode_t;
import jnr.ffi.types.off_t;
import jnr.ffi.types.size_t;
import ru.serce.jnrfuse.ErrorCodes;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.FuseStubFS;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.FuseFileInfo;
import ru.serce.jnrfuse.struct.Statvfs;

import java.nio.file.Paths;

import static jnr.ffi.Platform.OS.WINDOWS;

public class StagingFS extends FuseStubFS {

    //private StagingDirectory rootDirectory = new StagingDirectory(getContext(),"");

    public StagingFS() {
        // Sprinkle some files around
        /*
        rootDirectory.add(new StagingFile(getContext(),"Sample file.txt", "Hello there, feel free to look around.\n"));
        rootDirectory.add(new StagingDirectory(getContext(),"Sample directory"));
        StagingDirectory dirWithFiles = new StagingDirectory(getContext(), "Directory with files");
        rootDirectory.add(dirWithFiles);
        dirWithFiles.add(new StagingFile(getContext(),"hello.txt", "This is some sample text.\n"));
        dirWithFiles.add(new StagingFile(getContext(),"hello again.txt", "This another file with text in it! Oh my!\n"));
        StagingDirectory nestedDirectory = new StagingDirectory(getContext(),"Sample nested directory");
        dirWithFiles.add(nestedDirectory);
        nestedDirectory.add(new StagingFile(getContext(),"So deep.txt", "Man, I'm like, so deep in this here file structure.\n"));
        */
    }

    @Override
    public int create(String path, @mode_t long mode, FuseFileInfo fi) {
        if (getPath(path) != null) {
            return -ErrorCodes.EEXIST();
        }
        StagingPath parent = getParentPath(path);
        if (parent instanceof StagingDirectory) {
            ((StagingDirectory) parent).mkfile(getLastComponent(path));
            return 0;
        }
        return -ErrorCodes.ENOENT();
    }


    @Override
    public int getattr(String path, FileStat stat) {
        StagingPath p = getPath(path);
        if (p != null) {
            p.getattr(stat);
            return 0;
        }
        return -ErrorCodes.ENOENT();
    }

    private String getLastComponent(String path) {
        while (path.substring(path.length() - 1).equals("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.isEmpty()) {
            return "";
        }
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /*
    private StagingPath getParentPath(String path) {
        return rootDirectory.find(path.substring(0, path.lastIndexOf("/")));
    }

    private StagingPath getPath(String path) {
        return rootDirectory.find(path);
    }
    */


    @Override
    public int mkdir(String path, @mode_t long mode) {
        if (getPath(path) != null) {
            return -ErrorCodes.EEXIST();
        }
        StagingPath parent = getParentPath(path);
        if (parent instanceof StagingDirectory) {
            ((StagingDirectory) parent).mkdir(getLastComponent(path));
            return 0;
        }
        return -ErrorCodes.ENOENT();
    }


    @Override
    public int read(String path, Pointer buf, @size_t long size, @off_t long offset, FuseFileInfo fi) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        if (!(p instanceof StagingFile)) {
            return -ErrorCodes.EISDIR();
        }
        return ((StagingFile) p).read(buf, size, offset);
    }

    @Override
    public int readdir(String path, Pointer buf, FuseFillDir filter, @off_t long offset, FuseFileInfo fi) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        if (!(p instanceof StagingDirectory)) {
            return -ErrorCodes.ENOTDIR();
        }
        filter.apply(buf, ".", null, 0);
        filter.apply(buf, "..", null, 0);
        ((StagingDirectory) p).read(buf, filter);
        return 0;
    }


    @Override
    public int statfs(String path, Statvfs stbuf) {
        if (Platform.getNativePlatform().getOS() == WINDOWS) {
            // statfs needs to be implemented on Windows in order to allow for copying
            // data from other devices because winfsp calculates the volume size based
            // on the statvfs call.
            // see https://github.com/billziss-gh/winfsp/blob/14e6b402fe3360fdebcc78868de8df27622b565f/src/dll/fuse/fuse_intf.c#L654
            if ("/".equals(path)) {
                stbuf.f_blocks.set(1024 * 1024); // total data blocks in file system
                stbuf.f_frsize.set(1024);        // fs block size
                stbuf.f_bfree.set(1024 * 1024);  // free blocks in fs
            }
        }
        return super.statfs(path, stbuf);
    }

    @Override
    public int rename(String path, String newName) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        StagingPath newParent = getParentPath(newName);
        if (newParent == null) {
            return -ErrorCodes.ENOENT();
        }
        if (!(newParent instanceof StagingDirectory)) {
            return -ErrorCodes.ENOTDIR();
        }
        p.delete();
        p.rename(newName.substring(newName.lastIndexOf("/")));
        ((StagingDirectory) newParent).add(p);
        return 0;
    }

    @Override
    public int rmdir(String path) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        if (!(p instanceof StagingDirectory)) {
            return -ErrorCodes.ENOTDIR();
        }
        p.delete();
        return 0;
    }

    @Override
    public int truncate(String path, long offset) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        if (!(p instanceof StagingFile)) {
            return -ErrorCodes.EISDIR();
        }
        ((StagingFile) p).truncate(offset);
        return 0;
    }

    @Override
    public int unlink(String path) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        p.delete();
        return 0;
    }

    @Override
    public int open(String path, FuseFileInfo fi) {
        return 0;
    }

    @Override
    public int write(String path, Pointer buf, @size_t long size, @off_t long offset, FuseFileInfo fi) {
        StagingPath p = getPath(path);
        if (p == null) {
            return -ErrorCodes.ENOENT();
        }
        if (!(p instanceof StagingFile)) {
            return -ErrorCodes.EISDIR();
        }
        return ((StagingFile) p).write(buf, size, offset);
    }


















    public static void main(String[] args) {
        StagingFS memfs = new StagingFS();
        try {
            String path;
            switch (Platform.getNativePlatform().getOS()) {
                case WINDOWS:
                    path = "J:\\";
                    break;
                default:
                    path = "/tmp/mntm";
            }
            memfs.mount(Paths.get(path), true, true);
        } finally {
            memfs.umount();
        }
    }
}
