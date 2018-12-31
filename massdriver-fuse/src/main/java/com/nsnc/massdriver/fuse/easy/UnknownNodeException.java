package com.nsnc.massdriver.fuse.easy;

public class UnknownNodeException extends Exception {
	public UnknownNodeException(String nodePath) {
		super("Unknown Node Path " + nodePath);
	}
}
