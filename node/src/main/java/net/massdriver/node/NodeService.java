package net.massdriver.node;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.Path;

public class NodeService {
    private final Node node;

    @Produces
    @Default
    public Node getNode() {
        System.out.println("Inject!");
        return node;
    }

    public NodeService(Node node) {
        this.node = node;
    }
}