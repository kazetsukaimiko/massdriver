package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.chunk.*;
import com.nsnc.massdriver.nitrite.deprecated.NitriteAsset;
import com.nsnc.massdriver.nitrite.deprecated.NitriteDriver;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.util.Benchmark;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class NitriteDriverTest extends FileSystemTest {

    protected static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    NitriteDriver nitriteDriver;
    @Before
    public void initStore() throws IOException {
        nitriteDriver = new NitriteDriver(randomEmptyDirectory);
    }

    @Test
    public void testStreamSpeed() throws IOException {
        logger.info("Loading Asset Data.");

        PathAsset asset = new PathAsset(randomFile);
        for (Trait trait : asset.getDescription().getTraits()) {
            logger.info(trait.getName()+":"+trait.getContent());
        }
        for (Trait trait : CryptUtils.hashAll(randomFile)) {
            logger.info(trait.getName()+":"+trait.getContent());
        }
    }

    @Test
    public void testFileHash() throws IOException {
        System.out.println(randomFile.toString());
        Benchmark.bench(() ->{
            CryptUtils.getHashAlgorithms().forEach(algorithm -> {
               System.out.println(algorithm + " hash: "+ CryptUtils.hash(algorithm, randomFile));
            });
        },"All hashes took ${millis}ms");
    }

    @Test
    public void testStoreFile() throws IOException {
        // Hash the original file, set asset information.
        logger.info("Loading Asset Data.");
        PathAsset asset = new PathAsset(randomFile);

        logger.info("Making ChunkStream.");
        Stream<? extends Chunk> chunkStream = ChunkUtils.chunkStream(randomFile);

        logger.info("Persisting to nitriteDriver.");
        nitriteDriver.persistAsset(asset);

        nitriteDriver.commit();
    }

    @Test
    public void testStorePathAsset() throws IOException {
        // Hash the original file, set asset information.
        logger.info("Loading Asset Data.");
        PathAsset asset = new PathAsset(randomFile); //.getAsset(executorService);


        logger.info("Persisting to nitriteDriver.");
        nitriteDriver.persistAsset(asset);

        nitriteDriver.commit();
    }


    @Test
    public void testStoreRetrievePathAsset() throws IOException {
        // Hash the original file, set asset information.
        logger.info("Loading Asset Data.");
        PathAsset asset = new PathAsset(randomFile); //.getAsset(executorService);

        logger.info("Asset URN: " + asset.getUrn());

        logger.info("Persisting to nitriteDriver.");
        UUID assetId = nitriteDriver.persistAsset(asset);
        NitriteAsset nitriteAsset = nitriteDriver.retrieveAsset(assetId);

        logger.info("Asset: "+assetId);

        logger.info("Read Asset URN: " + nitriteAsset.getUrn());

        Path randomCopy = randomPath(randomDirectory);

        ChunkUtils.writeChunkStream(randomCopy, asset.stream());

        Description original = new Description(CryptUtils.hashAll(randomFile));
        Description copy = new Description(CryptUtils.hashAll(randomFile));

        Assert.assertEquals("All hashes match...", original, copy);


        nitriteDriver.commit();

        final int[] count = new int[]{0};
        nitriteDriver.findAssets().forEach(a -> count[0]++);
        Assert.assertNotEquals(0, count[0]);
        System.out.println("Asset count: "+ count[0]);

    }




    @Test
    public void testSeedDirectory() throws IOException {
        Path seedDirectory = getSeedDirectory();
        NitriteDriver nd = new NitriteDriver(seedDirectory);

        Path baseContent1 = Paths.get(seedDirectory.toAbsolutePath().toString(), "baseContent1");
        if (!Files.exists(baseContent1)) {
            randomContent(baseContent1, 1021 * 769 * 52);
            PathAsset pathAsset1 = new PathAsset(baseContent1);
            UUID assetId1 = nd.persistAsset(pathAsset1);

            nd.renameAsset(assetId1, "baseContent1");
        }
        Path baseContent2 = Paths.get(seedDirectory.toAbsolutePath().toString(), "baseContent2");
        if (!Files.exists(baseContent2)) {
            randomContent(baseContent2, 1011 * 1348 * 25);
            PathAsset pathAsset2 = new PathAsset(baseContent2);
            UUID assetId2 = nd.persistAsset(pathAsset2);
            nd.renameAsset(assetId2, "baseContent2");
        }
        nd.commit();
        nd.close();
    }



    @After
    public void closeStore() throws IOException {
        Assert.assertFalse(nitriteDriver.hasUnsavedChanges());
        nitriteDriver.commit();
        nitriteDriver.close();
    }

}
