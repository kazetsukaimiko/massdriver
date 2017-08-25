package net.massdriver.orbit.rest.serviceclient;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.massdriver.orbit.model.tokyotosho.Rss;

@Path("rss.php")
//@Service(baseUrl="http://tokyotosho.info/")
@Produces("application/xml")
public interface TokyoToshoService {
  @GET
  public Rss getFeed();

  @GET
  public Rss getFeed(@QueryParam("filter") String filter);
}
