package net.massdriver.orbit.rest.endpoints;

import com.nsnc.massdriver.nitrite.NitriteDriver;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@ApplicationScoped
@Path("/hello")
@Produces("application/json")
@Consumes("application/json")
public class HelloWorldEndpointImpl implements HelloWorldEndpoint {

    @Override
    public String sayHello() {
    return "Hello World";
  }
}