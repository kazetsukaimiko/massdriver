package com.nsnc.massdriver.crypt;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class Pool<K> {

    private static final Logger LOGGER = Logger.getLogger(Pool.class.getName());
    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private int size = 0;
    private Map<String, K> keys = new HashMap<>();


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, K> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, K> keys) {
        this.keys = keys;
    }

    public abstract K generate() throws NoSuchAlgorithmException;
    public abstract String uniqueIdentifier(K key);

    public void clear() {
        keys = new HashMap<>();
    }

    private static <K> Optional<K> safeFetch(Future<K> futureKey) {
        try {
            return Optional.of(futureKey.get());
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Exception fetching: ", e);
            return Optional.empty();
        } catch (ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Exception fetching: ", e);
            return Optional.empty();
        }
    }

    public void populate(int size) throws NoSuchAlgorithmException {
       this.size = size;
       populate();
    }

    public void populate() throws NoSuchAlgorithmException {
        List<Future<K>> futureKeys = IntStream.range(0, size - keys.size())
                .mapToObj(i -> executor.submit(this::generate))
                .collect(Collectors.toList());
        keys = futureKeys.stream()
                .map(Pool::safeFetch)
                .flatMap(Optional::stream)
                .collect(Collectors.toMap(
                        this::uniqueIdentifier,
                        Function.identity(),
                        (k1, k2) -> k2
                ));
    }


}
