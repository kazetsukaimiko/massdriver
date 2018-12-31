package com.nsnc.massdriver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.driver.Driver;

public abstract class DriverTest extends FileSystemTest {

    public abstract Driver getDriver();

    @BeforeEach
    public void testDriverFunctionality() throws IOException {
        System.out.println("DriverTest.before");
        Asset a = getDriver().persist(this.randomFile);

        a.getDescription()
                .getTraits()
                .forEach(System.out::println);

        System.out.println("File size: \n"+Files.size(randomFile));

        List<Chunk> memoryChunks = a.getChunkInfo().stream()
                .map(getDriver()::retrieveChunk)
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
        assertEquals((double) a.getChunkInfo().size(), Math.ceil(Files.size(randomFile)/((double) Chunk.DEFAULT_CHUNK_SIZE)));

        long allChunksSize = a.getChunkInfo().stream()
                .map(getDriver()::retrieveChunk)
                .flatMap(Optional::stream)
                .map(Chunk::getLength)
                .mapToLong(l -> l)
                .sum();

        assertEquals(Files.size(randomFile), allChunksSize);
    }




}
