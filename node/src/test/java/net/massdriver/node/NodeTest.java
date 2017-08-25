package net.massdriver.node;

import jdk.nashorn.internal.parser.Scanner;
import net.massdriver.node.model.Peer;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NodeTest {

    @Test
    public void testNode() throws IOException, InterruptedException {
        int port = 8787;
        Node node = new Node(port);
        URL url = new URL("http://localhost:"+port+"/hello");
        Assert.assertEquals("Hello World", getContent(url));
    }

    @Test
    public void testNodePeers() {
        List<Integer> ports = streamRange(8787, 10);
        LinkedHashMap<Integer, Node> nodes = new LinkedHashMap<>();
        List<Integer> portsInPlay = new ArrayList<>();
        while (ports.size()>1) {
            int port1 = ports.remove(0);
            int port2 = ports.remove(0);
            Node node1 = new Node(port1);
            Node node2 = new Node(port2);
            node1.addPeers(node2.getMe());
            if (portsInPlay.size()>0) {
                nodes
                    .get(portsInPlay.get(portsInPlay.size()-1))
                    .addPeers(node1.getMe());
            }
            portsInPlay.add(port1);
            portsInPlay.add(port2);

            nodes.put(port1, node1);
            nodes.put(port2, node2);
        }

        for (Map.Entry<Integer, Node> nodePort : nodes.entrySet()) {
            Integer port = nodePort.getKey();
            Node node = nodePort.getValue();

            System.out.println("\n\nNode "+port);
            System.out.println("######################");
            for (Peer peer : node.getPeers()) {
                System.out.println(peer);
            }
            System.out.println("######################");

        }

    }

    List<Integer> without(List<Integer> items, Integer item) {
        List<Integer> ints = new ArrayList<Integer>();
        ints.remove(item);
        return ints;
    }

    private List<Integer> streamRange(int from, int limit) {
        return IntStream.range(from, from+limit)
                .boxed()
                .collect(Collectors.toList());
    }

    private String getContent(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        String pageText = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            pageText = reader.lines().collect(Collectors.joining("\n"));
        }
        return pageText;
    }
}
