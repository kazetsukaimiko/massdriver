package com.nsnc.massdriver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.FileAsset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkMetadata;
import com.nsnc.massdriver.chunk.Location;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.driver.MapAccessDriver;
import com.nsnc.massdriver.index.RecursingIndexer;

public abstract class DriverTest<T extends Driver> extends FileSystemTest {

    private T driver;
    public abstract T makeDriver() throws IOException;

    @BeforeEach
    public void setupDriver() throws IOException {
        driver = makeDriver();
    }

    public T getDriver() {
        return driver;
    }




}
