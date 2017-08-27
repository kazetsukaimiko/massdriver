package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.chunk.PathAsset;
import com.nsnc.massdriver.nitrite.deprecated.NitriteDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
