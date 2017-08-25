package net.massdriver.node.annotation;

import net.massdriver.node.endpoints.NodeClient;
import net.massdriver.node.model.Peer;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.massdriver.node.annotation.PeerClient.FetchMode.*;

@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface PeerClient {
    @Nonbinding Class<? extends NodeClient> clientClass() default NodeClient.class;
    @Nonbinding String hostname() default "";
    @Nonbinding int port() default 0;

    /**
     * Find the fastest peer for the request
     * @return
     */
    @Nonbinding boolean best() default false;

    /**
     * Determines when to give up on the request, or finish aggregate.
     * @return
     */
    @Nonbinding int timeout() default 10000;

    //@Nonbinding FetchMode singularStrategy() default FASTEST;
    @Nonbinding FetchMode pluralStrategy() default FASTEST;

    static enum FetchMode {
        FASTEST, // Drop other requests and return the fastest results
        AGGREGATE // Aggregate all results together.
    }
}
