package net.massdriver.orbit.rest.serviceclient;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;

import net.massdriver.orbit.model.horriblesubs.Rss;

@Path("rss.php")
//@Service(baseUrl="http://horriblesubs.info/")
@Produces("application/xml")
public interface HorribleSubsService {
  //@GET
  //public Rss getFeed();

  @GET
  public Rss getFeed(@DefaultValue("all") @QueryParam("res") String resolutions, @DefaultValue("450") @QueryParam("entries") Integer entries);
}
