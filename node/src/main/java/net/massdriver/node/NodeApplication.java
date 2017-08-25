package net.massdriver.node;

import org.reflections.Reflections;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class NodeApplication extends Application {
    private static final Reflections reflections = new Reflections(Node.class.getPackage().getName());
    private final Set<Class<?>> classes;
    private final Set<Object> singletons;

    public static void main(String[] args) {
        Node node = new Node(8080);
    }

    public NodeApplication(Node node) {
        classes = reflections.getTypesAnnotatedWith(Path.class).stream()
                .filter(klazz -> !klazz.isInterface())
                .filter(klazz -> !Modifier.isAbstract(klazz.getModifiers()))
                .collect(Collectors.toSet());

        singletons = reflections.getTypesAnnotatedWith(Singleton.class).stream()
                .filter(singletonClass -> !Objects.equals(NodeService.class,singletonClass))
                .map(singletonClass -> {
                    try {
                        return singletonClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        singletons.add(new NodeService(node));
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }


}
