package net.massdriver.orbit.magnet;

import java.io.InputStream;
import java.util.Set;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;

import org.mongodb.morphia.Datastore;
import com.mongodb.gridfs.GridFS;

import net.massdriver.orbit.config.Configuration;
import net.massdriver.orbit.entity.DownloadJob;

@ApplicationScoped
public class MagnetDownloader {
    
  @Inject
  private Datastore datastore;
  
  @Inject
  private GridFS gridFS;
  
  @Inject
  private Configuration config;

  @Resource
  private ManagedExecutorService pool;
  
  private Set<DownloadJob> downloadJobs;
    
  private DownloadJob downloadMagnetAsync(final String magnetLink) {
    return new DownloadJob(
      pool.submit(() -> downloadMagnet(magnetLink))
    );
  }

  public static InputStream downloadMagnet(String magnetLink) {
    return null;
  }

}