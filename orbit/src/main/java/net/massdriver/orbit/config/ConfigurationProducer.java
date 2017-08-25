package net.massdriver.orbit.config;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class ConfigurationProducer {
  private final Logger logger = Logger.getLogger(getClass().getName());
 
  protected ObjectMapper mapper = new ObjectMapper();

  public Path getConfigPath() throws IOException {
    Path parent = Paths.get(System.getProperty("user.home"), "/.orbit");
    try {
      if (!Files.isDirectory(parent)) {
        Files.createDirectories(parent);
      }
    } catch (IOException ioe) {
      logger.log(Level.SEVERE, "Could not create configuration directory ~/.orbit. Falling back to CWD... " , ioe);
    }
    if (Files.isDirectory(parent)) {
      Path configPath = Paths.get(System.getProperty("user.home"), "/.orbit/", "config.json");
      return configPath;
    } else {
      String cwd = System.getProperty("user.dir");
      Path configPath = Paths.get(cwd, "./config.json");
      return configPath;
    }
  }

  @Produces @Default
  public MongoDBConfig makeMongoDBConfig() throws IOException {
    Configuration config = makeConfiguration();
    return config.getMongoDBConfig();
  }

  @Produces @Default
  public Configuration makeConfiguration() throws IOException {
    Path configPath = getConfigPath();
    logger.info("Config Path: " + configPath.toString());
    try {
      if (Files.exists(configPath)) {
        logger.info("Loading Config.");
        return loadConfig(configPath);
      } else {
        logger.info("Creating Config.");
        return createConfig(configPath);
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Exception making config: ", e);
    } return new Configuration();
  }
  
  public Configuration loadConfig(Path configPath) throws IOException {
    Configuration stashScannerConfig = mapper.readValue(configPath.toFile(), Configuration.class);
    persistConfig(configPath, stashScannerConfig);
    return stashScannerConfig;
  }
  
  public Configuration createConfig(Path configPath) throws IOException {
    Configuration stashScannerConfig = new Configuration();
    persistConfig(configPath, stashScannerConfig);
    return stashScannerConfig;
  }
  
  public Configuration persistConfig(Path configPath, Configuration stashScannerConfig) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(configPath.toFile(), stashScannerConfig);
    return stashScannerConfig;
  }
  

  public static String runtimeProperty(String systemProperty, String defaultValue) {
    String systemPropertyValue = System.getProperty(systemProperty);
    if (systemPropertyValue != null) {
      return systemPropertyValue;
    } return defaultValue;
  }
}
