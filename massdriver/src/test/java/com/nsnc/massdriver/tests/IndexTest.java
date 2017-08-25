package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.chunk.PathAsset;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.nitrite.NitriteDriver;
import com.nsnc.massdriver.util.Benchmark;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class IndexTest extends FileSystemTest {

    protected static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    NitriteDriver nitriteDriver;
    @Before
    public void initStore() throws IOException {
        nitriteDriver = new NitriteDriver(randomEmptyDirectory);
    }

    @Test
    public void testIndex() throws IOException {
        logger.info("Loading Asset Data.");

        PathAsset asset = new PathAsset(randomFile);
        nitriteDriver.persistIndex(asset);
        nitriteDriver.commit();

        PathAsset index = nitriteDriver.retrieveIndex(randomFile);

        Assert.assertEquals("Generated asset and Index", asset.getDescription(), index.getDescription());
        Assert.assertEquals("Chunk Data", asset.getChunkInfo(), index.getChunkInfo());

    }

    @After
    public void closeStore() {
        Assert.assertFalse(nitriteDriver.hasUnsavedChanges());
        nitriteDriver.commit();
        nitriteDriver.close();
    }

}
