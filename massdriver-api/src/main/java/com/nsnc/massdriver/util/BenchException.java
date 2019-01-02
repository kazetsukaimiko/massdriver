package com.nsnc.massdriver.util;

public class BenchException extends RuntimeException {
	public BenchException() {
	}

	public BenchException(String s) {
		super(s);
	}

	public BenchException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public BenchException(Throwable throwable) {
		super("Exception thrown when benchmarking", throwable);
	}

	public BenchException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}