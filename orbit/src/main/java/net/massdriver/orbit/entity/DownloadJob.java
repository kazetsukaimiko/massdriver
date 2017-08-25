package net.massdriver.orbit.entity;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DownloadJob {
  private LocalTime created = LocalTime.now();
  private LocalTime expires;
  private Future<InputStream> magnetStream = null;

  private static final Duration twoMinutes = Duration.ofMinutes(2);
  
  public DownloadJob(LocalTime expires, Future<InputStream> magnetStream) {
    this.expires = expires;
    this.magnetStream = magnetStream;
  }

  public DownloadJob(Duration expiresIn, Future<InputStream> magnetStream) {
    this(LocalTime.now().plus(expiresIn), magnetStream);
  }

  public DownloadJob(long expiresIn, ChronoUnit unit, Future<InputStream> magnetStream) {
    this(LocalTime.now().plus(expiresIn, unit), magnetStream);
  }

  public DownloadJob(Future<InputStream> magnetStream) {
    this(LocalTime.now().plus(twoMinutes), magnetStream);
  }
  
  public boolean cancel(boolean mayInterruptIfRunning) {
    return magnetStream.cancel(mayInterruptIfRunning);
  }
  
  public InputStream get() throws ExecutionException, InterruptedException {
    return magnetStream.get();
  }

  public InputStream get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return magnetStream.get(timeout, unit);
  }
  
  public boolean isCancelled() {
    return magnetStream.isCancelled();
  }
  
  public boolean isDone() {
    return magnetStream.isDone();
  }
  
}
