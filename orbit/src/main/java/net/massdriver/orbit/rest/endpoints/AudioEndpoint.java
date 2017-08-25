package net.massdriver.orbit.rest.endpoints;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.spi.MixerProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@Path("/audio")
@Produces("application/json")
public interface AudioEndpoint { //

  @GET
  @Path("/test/{length}")
  public String testMixer(@PathParam("length") Integer length) throws Exception;
  
  @GET
  @Path("/mixers")
  public List<Mixer.Info> getMixers();
}