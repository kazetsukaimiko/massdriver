package com.nsnc.massdriver.tests;


import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.MemoryChunk;
import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.driver.MapAccessDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DriverTest extends FileSystemTest {

    protected Driver driver;

    @Before
    public void init() {
        driver = new MapAccessDriver();
    }


    @Test
    public void testDriverFunctionality() throws IOException {
        Asset a = driver.persist(this.randomFile);

        a.getDescription()
                .getTraits()
                .forEach(System.out::println);

        System.out.println("File size: \n"+Files.size(randomFile));

        List<Chunk> memoryChunks = a.getChunkInfo().stream()
                .map(driver::retrieveChunk)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        System.out.println("Chunk sizes:");
        memoryChunks.stream()
                .map(Chunk::getLength)
                //.distinct()
                .sorted()
                .map(l -> l/1024+"k")
                .forEach(System.out::println);



        // Number of chunks match?
        assertThat((double) a.getChunkInfo().size(), equalTo(Math.ceil(Files.size(randomFile)/((double) Chunk.DEFAULT_CHUNK_SIZE))));

        long allChunksSize = a.getChunkInfo().stream()
                .map(driver::retrieveChunk)
                .flatMap(Optional::stream)
                .map(Chunk::getLength)
                .mapToLong(l -> l)
                .sum();

        assertThat(allChunksSize, equalTo(Files.size(randomFile)));
    }




}
