package net.massdriver.orbit.config;

public class MongoDBConfig extends RuntimeConfig {

  public final static String MONGO_HOST_PROPERTY = "mongoDBConfig.hostname";
  public final static String MONGO_PORT_PROPERTY = "mongoDBConfig.port";
  public final static String MONGO_DB_PROPERTY   = "mongoDBConfig.dbName";

  private String hostname = runtimeProperty(MONGO_HOST_PROPERTY, "localhost");
  public String getHostname() {
    return hostname;
  }
  public void setHostname(String aHostname) {
    hostname = aHostname;
  }
  public MongoDBConfig hostname(String aHostname) {
    setHostname(aHostname); return this;
  }

  private Integer port = new Integer(runtimeProperty(MONGO_PORT_PROPERTY, "27017"));
  public Integer getPort() {
    return port;
  }
  public void setPort(Integer aPort) {
    port = aPort;
  }
  public MongoDBConfig port(Integer aPort) {
    setPort(aPort); return this;
  }

  private String dbName = runtimeProperty(MONGO_DB_PROPERTY, "stash");
  public String getDbName() {
    return dbName;
  }
  public void setDbName(String aDbName) {
    dbName = aDbName;
  }
  public MongoDBConfig dbName(String aDbName) {
    setDbName(aDbName); return this;
  }

}
