package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.chunk.Chunk;
import com.nsnc.massdriver.chunk.ChunkUtils;
import com.nsnc.massdriver.crypt.CryptUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by luna on 8/7/17.
 */
public class ChunkUtilsTest extends FileSystemTest {

    @Test
    public void testMemoryCopy() throws IOException {
        testCopy(ChunkUtils.chunkStream(randomFile));
    }

    public void testCopy(Stream<? extends Chunk> chunkStream) throws IOException {
        Path randomCopy = randomPath(randomDirectory);
        ChunkUtils.writeChunkStream(randomCopy, chunkStream);
        Assert.assertEquals("Files Must Match Size", Files.size(randomFile), Files.size(randomCopy));
    }

    @Test
    public void testMemoryCopyIntegrity() throws IOException {
        testCopyIntegrity(ChunkUtils.chunkStream(randomFile));
    }
    public void testCopyIntegrity(Stream<? extends Chunk> chunkStream) throws IOException {
        logger.info("Hashing File.");
        String fileHash = CryptUtils.hash("MD5", randomFile);
        logger.info("Enter TestCopy");
        logger.info("Making copy Path");
        Path randomCopy = randomPath(randomDirectory);
        logger.info("Issuing Copy");
        ChunkUtils.writeChunkStream(randomCopy, chunkStream);
        logger.info("Hashing Copy...");
        String copyHash = CryptUtils.hash("MD5", randomCopy);

        Assert.assertEquals("Files Must Match", fileHash, copyHash);
    }

    @Test
    public void testIndex() {

    }

}
