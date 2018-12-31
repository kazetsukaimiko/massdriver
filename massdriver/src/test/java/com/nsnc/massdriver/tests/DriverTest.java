package com.nsnc.massdriver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.driver.Driver;

    public abstract Driver make() throws IOException;

    @Test
    public void basicTest() throws IOException {
        basicTest(make());
    }

    @Test
    public void indexTest() throws IOException {
        indexTest(make());
    }

    @Test
    public void writebackTest() throws IOException {
        Driver driver = make();
        indexTest(driver);
        writebackTest(driver);
    }

    private void writebackTest(Driver driver) throws IOException {
        for(Asset asset : driver.allAssets().collect(Collectors.toList())) {
            writebackTest(driver, asset);
        }
    }

    private void writebackTest(Driver driver, Asset asset) throws IOException {
        String randomFileName = IntStream.range(0,4).mapToObj(i -> randomName()).collect(Collectors.joining("-"));
        Path randomFilePath = Paths.get(randomDirectory.toAbsolutePath().toString(), randomFileName);
        driver.writeToPath(asset.getTraits(), randomFilePath);

        List<Trait> traits = CryptUtils.hashAll(randomFilePath);

        assertTrue(Trait.matches(asset.getTraits(), traits));

        //asset.getChunkMetadata()
    }


    public void basicTest(Driver driver) throws IOException {
        FileAsset fileAsset = new FileAsset(this.randomFile);
        List<Trait> traitList = driver.persistAsset(fileAsset);
        Optional<Asset> newAsset = driver.retrieveAsset(traitList);
        newAsset
                .ifPresent(a -> {
                    try {
                        assetTest(a, driver);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
        assertTrue(newAsset.isPresent());


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
