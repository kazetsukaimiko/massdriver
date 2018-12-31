package com.nsnc.massdriver.fuse.easy;

import java.nio.file.Paths;
import java.util.Objects;

public abstract class EasyNode {
	private final String name;
	private final long permissions;
	private EasyDirectory parent;

	protected EasyNode(String name, long permissions) {
		this.name = name;
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return Paths.get(getParent() != null ? getParent().getPath() : "/", getName()).toString();
	}

	public long getPermissions() {
		return permissions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		EasyNode easyNode = (EasyNode) o;
		return Objects.equals(getPath(), easyNode.getPath());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPath());
	}

	public void setParent(EasyDirectory parent) {
		this.parent = parent;
	}

	public EasyDirectory getParent() {
		return parent;
	}
}
