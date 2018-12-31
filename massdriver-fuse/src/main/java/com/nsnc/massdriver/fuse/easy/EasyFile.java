package com.nsnc.massdriver.fuse.easy;

public abstract class EasyFile extends EasyNode {
	private long size;

	public EasyFile(String name, long permissions) {super(name, permissions);}

	public EasyFile(String name) {
		super(name, 0555);
	}

	public abstract byte[] readRange(long size, long offset);

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
}
