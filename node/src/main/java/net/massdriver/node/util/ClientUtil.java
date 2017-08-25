package net.massdriver.node.util;

import net.massdriver.node.endpoints.NodeClient;
import net.massdriver.node.model.Peer;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.weld.bean.proxy.ClientProxyFactory;

import javax.ws.rs.client.Client;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ClientUtil {

    public static <T extends NodeClient> T getClient(Class<T> nodeClientClass, Peer peer) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://"+peer.toString()+"/");
        return target.proxy(nodeClientClass);
    }
}
