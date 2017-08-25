package net.massdriver.node.model;

import net.massdriver.node.annotation.PeerClient;

import java.util.Objects;

public class Peer implements Comparable<Peer> {
    private String hostname;
    private int port;
    private Long time = Long.MAX_VALUE;

    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    public long getTime() { return time; }
    public void setTime(long time) { this.time = time; }

    public Peer(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public Peer() {
    }

    @Override
    public String toString() {
        return hostname+":"+port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peer peer = (Peer) o;
        return port == peer .port &&
                Objects.equals(hostname, peer.hostname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, port);
    }

    @Override
    public int compareTo(Peer peer) {
        return peer != null ? time.compareTo(peer.getTime()) : 1;
    }
}
