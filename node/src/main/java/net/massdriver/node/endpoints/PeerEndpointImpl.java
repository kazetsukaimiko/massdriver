package net.massdriver.node.endpoints;


import net.massdriver.node.model.Peer;
import net.massdriver.node.util.ClientUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/peer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PeerEndpointImpl implements PeerEndpoint {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private Set<Peer> peers = Collections.synchronizedSet(new LinkedHashSet<Peer>());

    @Context
    HttpServletRequest request;


    @Override
    @GET
    public Set<Peer> getPeers() {
        return peers;
    }

    @Override
    @POST
    public Set<Peer> addPeers(List<Peer> peers) {
        peers.remove(getMe());
        peers.removeAll(this.peers);
        if (peers.size()>0) {
            getPeers().addAll(peers);
            peers.forEach(this::tryToFindPeers);
        }
        return getPeers();
    }

    // TODO: Security.
    @Override
    @DELETE
    public Set<Peer> removePeers(List<Peer> peers) {
        peers.forEach(this.peers::remove);
        return getPeers();
    }

    private void tryToFindPeers(Peer peer) {
        try {
            PeerEndpoint ofPeerEndpoint = ofPeer(peer);
            addPeers(new ArrayList<Peer>(ofPeerEndpoint.addPeers(Arrays.asList(getMe()))));
        } catch (Exception e) {
            logger.log(Level.WARNING, "Couldn't fetch peers of peer: "+peer, e);
        }
    }

    public static PeerEndpoint ofPeer(Peer peer) {
        return ClientUtil.getClient(PeerEndpoint.class, peer.getHostname(), peer.getPort());
    }

    public Peer getMe() {
        return new Peer(request.getRemoteAddr(), request.getLocalPort());
    }
}

