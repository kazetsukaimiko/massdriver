package net.massdriver.orbit.rest.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public interface HelloWorldEndpoint {

  @GET
  public String sayHello();

}