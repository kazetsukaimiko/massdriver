package net.massdriver.node.endpoints;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Random;
import java.util.stream.IntStream;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeEndpointImpl implements TimeEndpoint, NodeClient {
    IntStream intStream = new Random().ints(10,1500);

    @GET
    public long getTime() throws InterruptedException {
        Thread.sleep(intStream.findFirst().getAsInt());
        return System.currentTimeMillis();
    }
}
