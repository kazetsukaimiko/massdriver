package com.nsnc.massdriver.util;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Benchmark {
    public static final Logger logger = Logger.getLogger(Benchmark.class.getName());


    public static void bench(Runnable runnable, String message) {
        bench(() -> { runnable.run(); return null; }, message);
    }

    public static <T> T bench(Callable<T> callable, String message) {
        long start = System.currentTimeMillis();
        try {
            T result = callable.call();
            long total = System.currentTimeMillis() - start;
            getLogger().info(message.replace("${millis}", String.valueOf(total)));
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Exception benchmarking.", e);
        }
    }

    private static Logger getLogger() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement elem = stack[4];
        return Logger.getLogger(elem.getFileName()+":"+elem.getMethodName()+":"+elem.getLineNumber());
    }
}
