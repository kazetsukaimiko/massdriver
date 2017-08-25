package net.massdriver.orbit.config;

import java.util.ArrayList;
import java.util.List;

public class Configuration extends RuntimeConfig {


  public final static String ICONIMG_PROPERTY = "iconImage";

  private MongoDBConfig mongoDBConfig = new MongoDBConfig();
  public MongoDBConfig getMongoDBConfig() {
    return mongoDBConfig;
  }
  public void setMongoDBConfig(MongoDBConfig aMongoDBConfig) {
    mongoDBConfig = aMongoDBConfig;
  }
  public Configuration mongoDBConfig(MongoDBConfig aMongoDBConfig) {
    setMongoDBConfig(aMongoDBConfig); return this;
  }

  private List<ScanConfiguration> scanConfiguration = new ArrayList<>();
  public List<ScanConfiguration> getScanConfiguration() {
    return scanConfiguration;
  }
  public void setScanConfiguration(List<ScanConfiguration> scanConfiguration) {
    this.scanConfiguration = scanConfiguration;
  }
  public Configuration scanConfiguration(List<ScanConfiguration> scanConfiguration) {
    this.scanConfiguration = scanConfiguration; return this;
  }

  private String iconImage = runtimeProperty(ICONIMG_PROPERTY, "resources/images/pepe.png");
  public String getIconImage() {
    return iconImage;
  }
  public void setIconImage(String aIconImage) {
    iconImage = aIconImage;
  }
  public Configuration iconImage(String aIconImage) {
    setIconImage(aIconImage); return this;
  }
  
  public Configuration() {
  }

}
