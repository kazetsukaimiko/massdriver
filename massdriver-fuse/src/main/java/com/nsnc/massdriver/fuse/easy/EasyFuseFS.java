package com.nsnc.massdriver.fuse.easy;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.nsnc.massdriver.fuse.easy.EasyDirectory;
import com.nsnc.massdriver.fuse.easy.EasyFile;
import com.nsnc.massdriver.fuse.easy.EasyNode;
import com.nsnc.massdriver.fuse.easy.UnknownNodeException;

import jnr.ffi.Pointer;
import jnr.ffi.types.off_t;
import jnr.ffi.types.size_t;
import ru.serce.jnrfuse.AbstractFuseFS;
import ru.serce.jnrfuse.ErrorCodes;
import ru.serce.jnrfuse.FuseFillDir;
import ru.serce.jnrfuse.struct.FileStat;
import ru.serce.jnrfuse.struct.Flock;
import ru.serce.jnrfuse.struct.FuseBufvec;
import ru.serce.jnrfuse.struct.FuseFileInfo;
import ru.serce.jnrfuse.struct.FusePollhandle;
import ru.serce.jnrfuse.struct.Statvfs;
import ru.serce.jnrfuse.struct.Timespec;

public abstract class EasyFuseFS extends AbstractFuseFS {

	private final Random RANDOM = new Random(System.currentTimeMillis());
	private final Set<Long> handles = new HashSet<>();

	public abstract EasyNode ofPath(String path) throws UnknownNodeException;

	private final long generateHandle() {
		long handle = RANDOM.nextLong();
		if (!handles.contains(handle)) {
			handles.add(handle);
			return handle;
		}
		return generateHandle();
	}

	private final int removeHandle(long handle) {
		if (handles.contains(handle)) {
			handles.remove(handle);
			return 0;
		}
		return -1;
	}

	@Override
	public int getattr(String path, FileStat stat) {
		int res = 0;
		try {
			EasyNode easyNode = ofPath(path);
			if (easyNode instanceof EasyDirectory) {
				stat.st_nlink.set(2);
				stat.st_mode.set(FileStat.S_IFDIR | easyNode.getPermissions());
			} else if (easyNode instanceof EasyFile) {
				stat.st_nlink.set(1);
				stat.st_size.set(((EasyFile) easyNode).getSize());
				stat.st_mode.set(FileStat.S_IFREG | easyNode.getPermissions());
			}
		} catch (UnknownNodeException e) {
			res = -ErrorCodes.ENOENT();
		}
		return res;
	}


	@Override
	public int readdir(String path, Pointer buf, FuseFillDir filter, @off_t long offset, FuseFileInfo fi) {
		EasyNode easyNode = null;
		try {
			easyNode = ofPath(path);
			if (easyNode instanceof EasyDirectory) {
				filter.apply(buf, ".", null, 0);
				filter.apply(buf, "..", null, 0);
				((EasyDirectory) easyNode).getChildren()
						.forEach(child -> filter.apply(buf, child.getName(), null, 0));
				return 0;
			}
		} catch (UnknownNodeException e) {
			e.printStackTrace();
		}

		return -ErrorCodes.ENOENT();
	}

	@Override
	public int open(String path, FuseFileInfo fi) {
		EasyNode easyNode = null;
		try {
			easyNode = ofPath(path);
			if (easyNode instanceof EasyFile) {
				fi.fh.set(generateHandle());
				return 0;
			}
		} catch (UnknownNodeException e) {
			e.printStackTrace();
		}

		return -ErrorCodes.ENOENT();
	}

	@Override
	public int read(String path, Pointer buf, @size_t long size, @off_t long offset, FuseFileInfo fi) {
		EasyNode easyNode = null;
		try {
			easyNode = ofPath(path);
			if (easyNode instanceof EasyFile) {
				fi.fh.set(generateHandle());
				byte[] bytes = ((EasyFile) easyNode).readRange(size, offset);
				buf.put(0, bytes,0, bytes.length);
				return bytes.length;
			}
		} catch (UnknownNodeException e) {
			e.printStackTrace();
		}

		return -ErrorCodes.ENOENT();
	}

	// Unnecessary?

	@Override
	public int readlink(String path, Pointer buf, long size) {
		return 0;
	}

	@Override
	public int mknod(String path, long mode, long rdev) {
		return 0;
	}

	@Override
	public int mkdir(String path, long mode) {
		return 0;
	}

	@Override
	public int unlink(String path) {
		return 0;
	}

	@Override
	public int rmdir(String path) {
		return 0;
	}

	@Override
	public int symlink(String oldpath, String newpath) {
		return 0;
	}

	@Override
	public int rename(String oldpath, String newpath) {
		return 0;
	}

	@Override
	public int link(String oldpath, String newpath) {
		return 0;
	}

	@Override
	public int chmod(String path, long mode) {
		return 0;
	}

	@Override
	public int chown(String path, long uid, long gid) {
		return 0;
	}

	@Override
	public int truncate(String path, long size) {
		return 0;
	}

	@Override
	public int write(String path, Pointer buf, long size, long offset, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int statfs(String path, Statvfs stbuf) {
		return 0;
	}

	@Override
	public int flush(String path, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int release(String path, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int fsync(String path, int isdatasync, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int setxattr(String path, String name, Pointer value, long size, int flags) {
		return 0;
	}

	@Override
	public int getxattr(String path, String name, Pointer value, long size) {
		return 0;
	}

	@Override
	public int listxattr(String path, Pointer list, long size) {
		return 0;
	}

	@Override
	public int removexattr(String path, String name) {
		return 0;
	}

	@Override
	public int opendir(String path, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int releasedir(String path, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int fsyncdir(String path, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public Pointer init(Pointer conn) {
		return null;
	}

	@Override
	public void destroy(Pointer initResult) {

	}

	@Override
	public int access(String path, int mask) {
		return 0;
	}

	@Override
	public int create(String path, long mode, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int ftruncate(String path, long size, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int fgetattr(String path, FileStat stbuf, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int lock(String path, FuseFileInfo fi, int cmd, Flock flock) {
		return 0;
	}

	@Override
	public int utimens(String path, Timespec[] timespec) {
		return 0;
	}

	@Override
	public int bmap(String path, long blocksize, long idx) {
		return 0;
	}

	@Override
	public int ioctl(String path, int cmd, Pointer arg, FuseFileInfo fi, long flags, Pointer data) {
		return 0;
	}

	@Override
	public int poll(String path, FuseFileInfo fi, FusePollhandle ph, Pointer reventsp) {
		return 0;
	}

	@Override
	public int write_buf(String path, FuseBufvec buf, long off, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int read_buf(String path, Pointer bufp, long size, long off, FuseFileInfo fi) {
		return 0;
	}

	@Override
	public int flock(String path, FuseFileInfo fi, int op) {
		return 0;
	}

	@Override
	public int fallocate(String path, int mode, long off, long length, FuseFileInfo fi) {
		return 0;
	}
}
