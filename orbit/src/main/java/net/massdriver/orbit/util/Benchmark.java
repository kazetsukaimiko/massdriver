package net.massdriver.orbit.util;

import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Benchmark {

  public static String replacei(String message, String target, String replacement) {
    return String.valueOf(message).replaceAll(Pattern.compile(Pattern.quote(String.valueOf(target)), Pattern.CASE_INSENSITIVE).pattern(), String.valueOf(replacement));
  }

  public static <RT> RT bench(Callable<RT> callable, String message) {
    final Logger logger = Logger.getLogger(Benchmark.class.getName());
    try {
      Long start = new Long(System.currentTimeMillis());
      RT returned = callable.call();
      Long total = new Long(System.currentTimeMillis() - start);
      logger.info(replacei(message, "${millis}", String.valueOf(total)));
      return returned;
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Exception benchmarking!", e);
    } return null;
  }

  public static void bench(Runnable runnable, String message) {
    final Logger logger = Logger.getLogger(Benchmark.class.getName());
    try {
      Long start = new Long(System.currentTimeMillis());
      runnable.run();
      Long total = new Long(System.currentTimeMillis() - start);
      logger.info(replacei(message, "${millis}", String.valueOf(total)));
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Exception benchmarking!", e);
    }
  }

}