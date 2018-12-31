package com.nsnc.massdriver.util;
@FunctionalInterface
public interface Failable {
	void call() throws Exception;
}
