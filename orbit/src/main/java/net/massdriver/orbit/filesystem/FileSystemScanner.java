package net.massdriver.orbit.filesystem;

import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileVisitor;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

import javax.inject.Inject;

//import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;


import net.massdriver.orbit.config.Configuration;
import net.massdriver.orbit.config.ScanConfiguration;

@javax.ws.rs.Path("/filesystem")
@Produces("application/json")
public class FileSystemScanner {

  private final Logger logger = Logger.getLogger(getClass().getName());

  @Inject
  private Configuration config;

  private static final String regex = ".*/regeps/regep00/studies/(?<study>[\\d\\w_]+)/data/(?<type>[\\d\\w_]+)/(?<dataset>[\\d\\w_]+)/\\.stash";

  private List<String> skipAllBut = Arrays.asList(new String[] {

  });

  @javax.ws.rs.Path("/scan")
  @GET
  public List<Path> getDirectories() throws Exception {
    logger.info("Making directories...");

    final List<Path> paths = new ArrayList<Path>();
    for(ScanConfiguration scanConfiguration : config.getScanConfiguration()) {
      paths.addAll(getDirectory(Paths.get(scanConfiguration.getDirectory())));
    }
    return paths;
  }

  public List<Path> getDirectory(Path startDir) throws Exception {
    logger.info("Making directories...");

    FileSystem fs = FileSystems.getDefault();
    final PathMatcher matcher = fs.getPathMatcher("regex:"+regex);

    final List<Path> paths = new ArrayList<Path>();

    FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<Path>() {
      final Pattern p = Pattern.compile(regex);

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException ioe) {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attribs) {
        boolean skip = true;
        for(String passIf : skipAllBut) {
          Pattern passPattern = Pattern.compile(passIf);
          if (passPattern.matcher(dir.toString()).matches()) {
            skip = false;
          }
        }
        if (skip) {
          return FileVisitResult.SKIP_SUBTREE;
        }

        if (p.matcher(dir.toString()).matches()) {
          logger.info("Found Stash Directory: " + dir.toString());
          paths.add(dir);
        }
        return FileVisitResult.CONTINUE;
      }
    };

    logger.info("Walking "+startDir.toString()+"...");
    Files.walkFileTree(startDir, matcherVisitor);

    logger.info("Found " + String.valueOf(paths.size()) + " Stash Directories.");
    return paths;
  }
  
}