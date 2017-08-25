package net.massdriver.node.endpoints;

import net.massdriver.node.model.Peer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/peer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PeerEndpoint extends NodeClient {
    @GET
    public Set<Peer> getPeers();
    @POST
    public Set<Peer> addPeers(List<Peer> peers);
    @DELETE
    public Set<Peer> removePeers(List<Peer> peers);
}
