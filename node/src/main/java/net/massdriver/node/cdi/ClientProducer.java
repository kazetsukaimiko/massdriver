package net.massdriver.node.cdi;

import net.massdriver.node.annotation.PeerClient;
import net.massdriver.node.endpoints.NodeClient;
import net.massdriver.node.endpoints.PeerEndpoint;
import net.massdriver.node.model.Peer;
import net.massdriver.node.util.ClientUtil;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientProducer implements PeerEndpoint {

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private final Logger logger = Logger.getLogger(getClass().getName());
    private Set<Peer> peers = Collections.synchronizedSet(new LinkedHashSet<Peer>());

    @SuppressWarnings("unchecked") // I know what I'm doing
    @Produces @PeerClient
    public NodeClient getNodeClient(InjectionPoint injectionPoint) {
        PeerClient peerClient = injectionPoint.getAnnotated().getAnnotation(PeerClient.class);

        PeerClientInvocationHandler handler
            = new PeerClientInvocationHandler(peerClient);
        return (NodeClient) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[] { peerClient.clientClass() },
            handler
        );
    }

    @Inject
    @PeerClient(clientClass=PeerEndpoint.class, best=true)
    private PeerEndpoint peerEndpoint;

    public List<Peer> prioritizePeers() {
        return peers
                .stream()
                .filter(peer -> peer.getTime() >= 0) // Negatives blew up.
                .sorted()
                .limit(10)
                .collect(Collectors.toList());
    }

    public Set<Peer> getPeers() {
        return peers;
    }

    public void proliferate(Peer peer) {
        peers.add(peer);
        if (peers.stream().filter(current -> current.getTime() > 0).count() < 1000) {
            addPeers(new ArrayList<>(peerEndpoint.getPeers()));
        }
    }

    public Set<Peer> addPeers(List<Peer> peers) {
        peers.forEach(this::proliferate);
        return getPeers();
    }

    public Set<Peer> removePeers(List<Peer> peers) {
        peers.forEach(this.peers::remove);
        return getPeers();
    }

    public class PeerClientInvocationHandler implements InvocationHandler {
        private final PeerClient peerClient;

        public PeerClientInvocationHandler(PeerClient peerClient) {
            this.peerClient = peerClient;
        }

        private List<Peer> getPriorityPeers() {
            if (peerClient.best()) { // Limit to these
                return Arrays.asList(new Peer(peerClient.hostname(), peerClient.port()));
            }   return prioritizePeers();
        }

        private Future<Object> futureInvoke(Peer peer, Method method, Object[] args) {
            return executorService.submit(() -> {
                NodeClient nodeClient = ClientUtil.getClient(peerClient.clientClass(), peer);
                return method.invoke(nodeClient, args);
            });
        }

        public boolean isClassCollection(Class c) {
            return Collection.class.isAssignableFrom(c); // || Map.class.isAssignableFrom(c);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Map<Peer, Future<Object>> jobs = getPriorityPeers().stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            peer -> futureInvoke(peer, method, args),
                            (u, v) -> {
                                throw new IllegalStateException(String.format("Duplicate key %s", u));
                            },
                            LinkedHashMap::new
                            ));


            return fastest(jobs);
        }

        public Object fastest(Map<Peer, Future<Object>> jobs) throws InterruptedException, TimeoutException, ExecutionException {
            // Fastest
            long startTime = System.currentTimeMillis();
            boolean done = false;
            Object retval = null;
            while (!done) {
                for (Peer peer : jobs.keySet()) {
                    Future<Object> job = jobs.get(peer);
                    if (job.isDone()) { // Employ "FASTEST" strategy.
                        peer.setTime(System.currentTimeMillis()-startTime);
                        logger.info("Winning peer: " + peer + " ("+peer.getTime()+"ms)");
                        retval = job.get(); break;
                    }
                }
                if (retval != null) {
                    break;
                }
                Thread.sleep(1);
                if (System.currentTimeMillis()-startTime >= peerClient.timeout()) {
                    throw new TimeoutException("NodeClient Method failed to return a respose within allotted time.");
                }
            }
            jobs.values().forEach(future -> future.cancel(true));
            return retval;

        }
    }
}
