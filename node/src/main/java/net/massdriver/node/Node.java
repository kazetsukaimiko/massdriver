package net.massdriver.node;

import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import net.massdriver.node.endpoints.PeerEndpoint;
import net.massdriver.node.endpoints.PeerEndpointImpl;
import net.massdriver.node.model.Peer;
import org.jboss.resteasy.cdi.CdiInjectorFactory;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

public class Node implements Closeable {
    private UndertowJaxrsServer server;
    private PeerEndpoint peerEndpoint;

    private final String bindAddress;
    private final Peer me;

    public Peer getMe() {
        return me;
    }

    public int getPort() {
        return getMe().getPort();
    }


    public Node() {
        this("0.0.0.0", 8080);
    }

    public Node(int bindPort) {
        this("0.0.0.0", bindPort);
    }

    public Node(String bindAddress, int bindPort) {
        this.bindAddress = bindAddress;
        me = new Peer("127.0.0.1", bindPort);
        init();
    }


    private void init() {
        if (server != null) {
            server.stop();
            server = null;
        }
        server = new UndertowJaxrsServer();
        Undertow.Builder builder = Undertow.builder()
                .addHttpListener(getPort(), bindAddress);
        server.start(builder);

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplication(new NodeApplication(this));
        deployment.setInjectorFactoryClass(CdiInjectorFactory.class.getName());

        DeploymentInfo di = server.undertowDeployment(deployment, "/");

        di.setClassLoader(getClass().getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Massdriver Node")
                .addListeners(Servlets.listener(org.jboss.weld.environment.servlet.Listener.class));

        server.deploy(di);

        peerEndpoint = PeerEndpointImpl.ofPeer(getMe());
    }



    @Override
    public void close() throws IOException {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    public Set<Peer> getPeers() {
        return peerEndpoint.getPeers();
    }

    public Set<Peer> addPeers(Peer... peers) {
        return addPeers(new ArrayList<Peer>(Arrays.asList(peers)));
    }
    public Set<Peer> addPeers(List<Peer> peers) {
        return peerEndpoint.addPeers(peers);
    }


    public Set<Peer> removePeers(Peer... peers) {
        return removePeers(new ArrayList<Peer>(Arrays.asList(peers)));
    }
    public Set<Peer> removePeers(List<Peer> peers) {
        return peerEndpoint.removePeers(peers);
    }

}
