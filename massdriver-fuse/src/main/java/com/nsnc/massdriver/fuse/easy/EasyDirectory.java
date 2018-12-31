package com.nsnc.massdriver.fuse.easy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EasyDirectory extends EasyNode {
	private final Set<EasyNode> children = new HashSet<>();

	public EasyDirectory(String name, long permissions) {super(name, permissions);}

	public EasyDirectory(String name) {
		this(name, 0555);
	}

	public static EasyDirectory root(long permissions) {
		return new EasyDirectory("/", permissions);
	}

	public Set<EasyNode> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	public EasyDirectory children(Stream<EasyNode> children) {
		children.forEach(this::addChild);
		return this;
	}

	public EasyDirectory children(EasyNode... children) {
		return children(Stream.of(children));
	}

	public final EasyDirectory addChild(EasyNode child) {
		children.add(child);
		child.setParent(this);
		return this;
	}

	public final EasyDirectory removeChild(EasyNode child) {
		children.remove(child);
		child.setParent(null);
		return this;
	}

	@Override
	public void setParent(EasyDirectory parent) {
		super.setParent(parent);
		getChildren().forEach(child -> child.setParent(this));
	}

	public Set<EasyNode> flatten() {
		return Stream.concat(
				Stream.of(this),
				children.stream()
					.flatMap(child -> (child instanceof EasyDirectory) ?
							((EasyDirectory) child).flatten().stream()
							:
							Stream.of(child))
		).collect(Collectors.toSet());
	}
}
