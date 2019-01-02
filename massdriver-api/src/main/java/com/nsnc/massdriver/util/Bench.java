package com.nsnc.massdriver.util;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Bench {
	private static final String SUBSTITUTE = "${millis}";
	private long lastEntry = System.currentTimeMillis();
	private final Logger logger;

	public Bench(Logger logger) {this.logger = logger;}

	/**
	 * Logs an entry manually (stateful, for successive logging)
	 * @param message Message to log
	 * @return Returns this Bench instance for continued logging
	 */
	public Bench log(String message) {
		lastEntry = log(message, this.lastEntry);
		return this;
	}

	/**
	 * Benchmark the execution of a lambda/method reference, returning output.
	 * @param callable Callable to run and benchmark
	 * @param message Message to log
	 * @param <T> Return output from the Callable.
	 * @return The result of the callable.
	 */
	public <T> T bench(Callable<T> callable, String message) {
		long start = System.currentTimeMillis();
		try {
			return callable.call();
		} catch (Exception e) {
			throw new BenchException(e);
		} finally {
			log(message, start);
		}
	}

	/**
	 * Benchmark the execution of a lambda/method reference, returning output.
	 * @param failable to run and benchmark
	 * @param message Message to log
	 */
	public void bench(Failable failable, String message) {
		long start = System.currentTimeMillis();
		try {
			failable.call();
		} catch (Exception e) {
			throw new BenchException(e);
		} finally {
			log(message, start);
		}
	}

	/**
	 * Formats the log entry to include the benchmark time in milliseconds.
	 * You can place this in your string by using SUBSTITUTE, otherwise it is appended.
	 * @param message Message to log
	 * @param timeInMillis Amount of time in millis taken to append to the message.
	 * @return Message with benchmark information inserted/appended
	 */
	private String formatEntry(String message, long timeInMillis) {
		if (message.contains(SUBSTITUTE)) {
			return message.replace(SUBSTITUTE, (timeInMillis + "ms"));
		}
		return message + " (took "+timeInMillis+"ms)";
	}

	/**
	 * Invokes the logger with a given message and bench start time.
	 * @param message Message to log
	 * @return New time in millis
	 */
	private long log(String message, long start) {
		long finish = System.currentTimeMillis();
		logger.info(formatEntry(message, finish - start));
		return finish;
	}

}
