package net.massdriver.node.annotation;

import net.massdriver.node.endpoints.NodeClient;

import javax.enterprise.util.AnnotationLiteral;

public class PeerClientLiteral extends AnnotationLiteral<PeerClient> implements PeerClient {
    private Class<? extends NodeClient> clientClass;
    private String hostname;
    private int port;
    private boolean best;
    private int timeout;
    private FetchMode pluralStrategy;

    public PeerClientLiteral(Class<? extends NodeClient> clientClass, String hostname, int port, boolean best, int timeout, FetchMode pluralStrategy) {
        this.clientClass = clientClass;
        this.hostname = hostname;
        this.port = port;
        this.best = best;
        this.timeout = timeout;
        this.pluralStrategy = pluralStrategy;
    }

    public PeerClientLiteral(PeerClient peerClient) {
        this(
                peerClient.clientClass(),
                peerClient.hostname(),
                peerClient.port(),
                peerClient.best(),
                peerClient.timeout(),
                peerClient.pluralStrategy()
        );
    }

    public PeerClientLiteral() {

    }

    @Override
    public Class<? extends NodeClient> clientClass() {
        return clientClass;
    }

    @Override
    public String hostname() {
        return hostname;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public boolean best() {
        return best;
    }

    @Override
    public int timeout() {
        return timeout;
    }

    @Override
    public FetchMode pluralStrategy() {
        return pluralStrategy;
    }

    public PeerClientLiteral clientClass(Class<? extends NodeClient> clientClass) {
        this.clientClass = clientClass; return this;
    }

    public PeerClientLiteral hostname(String hostname) {
        this.hostname = hostname; return this;
    }

    public PeerClientLiteral port(int port) {
        this.port = port; return this;
    }

    public PeerClientLiteral best(boolean best) {
        this.best = best; return this;
    }

    public PeerClientLiteral timeout(int timeout) {
        this.timeout = timeout; return this;
    }

    public PeerClientLiteral pluralStrategy(FetchMode pluralStrategy) {
        this.pluralStrategy = pluralStrategy; return this;
    }
}
