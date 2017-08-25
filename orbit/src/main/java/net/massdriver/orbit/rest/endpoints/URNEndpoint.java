package net.massdriver.orbit.rest.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/urn")
@Produces("application/json")
@Consumes("application/json")
public interface URNEndpoint {

  @GET
  public String makeURN();

}