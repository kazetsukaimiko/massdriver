package net.massdriver.orbit.config;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.net.UnknownHostException;

import javax.inject.Inject;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.context.ApplicationScoped;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;

@ApplicationScoped
public class MongoDBProducer {
  private final Logger logger = Logger.getLogger(getClass().getName());
  
  @Inject
  private MongoDBConfig config;
 
  private MongoClient mongoClient;
  private Morphia morphia;
  private Datastore datastore;
  private DB db;
  private GridFS gridFS;
  
  @Produces @Default
  public MongoClient getMongoClient() throws UnknownHostException {
    if (mongoClient == null) {
      mongoClient = new MongoClient(
        config.getHostname(), config.getPort()
      );
    } return mongoClient;
  }
  
  @Produces @Default
  public DB getMongoDB() throws UnknownHostException {
    if (db == null) {
      MongoClient client = getMongoClient();
      db = client.getDB(config.getDbName());
    } return db;
  }
  @Produces @Default
  public GridFS getGridFS() throws UnknownHostException {
    if (gridFS == null) {
      DB db = getMongoDB();
      gridFS = new GridFS(db);
    } return gridFS;
  }
  @Produces @Default
  public Morphia getMorphia() {
    if (morphia == null) {
      morphia = new Morphia();
    } return morphia;
  }
  @Produces @Default
  public Datastore getDatastore() throws UnknownHostException {
    if (datastore == null) {
      Morphia morphia = getMorphia();
      datastore = morphia.createDatastore(getMongoClient(), config.getDbName());
    } return datastore;
  }
}
