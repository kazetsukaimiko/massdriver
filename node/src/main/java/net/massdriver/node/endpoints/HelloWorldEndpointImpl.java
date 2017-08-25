package net.massdriver.node.endpoints;

import net.massdriver.node.Node;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloWorldEndpointImpl implements HelloWorldEndpoint {
    @GET
    public String sayHello() {
        return "Hello World";
    }

    @Inject
    private Node node;

    @GET
    @Path("/port")
    public String getPort() {
        return String.valueOf(node.getPort());
    }
}

