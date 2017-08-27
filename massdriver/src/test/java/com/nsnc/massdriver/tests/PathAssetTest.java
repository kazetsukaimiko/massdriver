package com.nsnc.massdriver.tests;


import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.chunk.PathAsset;

import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.nitrite.deprecated.NitriteAsset;
import com.nsnc.massdriver.nitrite.deprecated.NitriteDriver;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class PathAssetTest extends FileSystemTest {

    NitriteDriver nitriteDriver;
    @Before
    public void initStore() throws IOException {
        nitriteDriver = new NitriteDriver(randomEmptyDirectory);
    }
    @Test
    public void writeTest() throws IOException {
        Asset pathAsset = new PathAsset(randomFile);
        nitriteDriver.persistAsset(pathAsset);
        nitriteDriver.commit();
    }

    @Test
    public void readTest() throws IOException {
        Asset pathAsset = new PathAsset(randomFile);
        Description description = pathAsset.getDescription();

        UUID assetId = nitriteDriver.persistAsset(pathAsset);
        nitriteDriver.commit();

        Path randomCopy = randomPath(randomDirectory);
        NitriteAsset nitriteAsset = nitriteDriver.retrieveAsset(assetId);

        ChunkUtils.writeAsset(randomCopy, nitriteAsset);

        Assert.assertEquals("Copy Matches", description, CryptUtils.makeDescription(randomCopy));
    }

    @After
    public void closeStore() {
        nitriteDriver.commit();
        nitriteDriver.close();
    }

}
