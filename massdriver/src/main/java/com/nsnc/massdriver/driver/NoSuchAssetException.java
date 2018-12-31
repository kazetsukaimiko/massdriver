package com.nsnc.massdriver.driver;

import com.nsnc.massdriver.Trait;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class NoSuchAssetException extends IOException {
    public NoSuchAssetException(Class<? extends Driver> driverClass, List<Trait> traits) {
        super(driverClass.getName() + " contains no asset matching: " + traits.stream().map(Trait::toString).collect(Collectors.joining("/")));
    }
}
