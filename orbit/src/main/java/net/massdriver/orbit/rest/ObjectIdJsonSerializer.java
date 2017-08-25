package net.massdriver.orbit.rest;

import java.util.logging.Logger;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;

public class ObjectIdJsonSerializer extends JsonSerializer<ObjectId> {

  private final Logger logger = Logger.getLogger(this.getClass().getName());

  @Override
  public void serialize(ObjectId id, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    jgen.writeString(marshal(id));
  }

  public ObjectId unmarshal( String s ) {
    //logger.info("UnMarshalling...");
    return new ObjectId(s);
  }
  public String marshal( ObjectId id ) {
    //logger.info("Marshalling...");
    if (id == null) {
      return "null";
    } else {
      return id.toString();
    }
  }
  
}  
