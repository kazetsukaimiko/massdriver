package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.crypt.CryptUtils;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.Comparator;
import java.util.Random;
import java.util.UUID;

/**
 * Created by luna on 8/7/17.
 */
public abstract class FileSystemTest extends MemoryTest {

    protected Path randomEmptyDirectory = null;
    protected Path randomDirectory = null;
    protected Path randomFile = null;

    @Before
    public void createRandomFiles() throws IOException {
        randomEmptyDirectory = randomDirectory();
        randomDirectory = randomDirectory();
        randomFile = randomFile(randomDirectory);
    }

    @After
    public void cleanupRandomFiles() throws IOException {
        deleteDirectory(randomDirectory);
        deleteDirectory(randomEmptyDirectory);
    }


    protected Path getSeedDirectory() throws IOException {
        return Files.createDirectories(Paths.get("/tmp/massdriver/seed/"));
    }

    private void deleteDirectory(Path directory) throws IOException {
        Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                //.peek(System.out::println)
                .forEach(File::delete);
    }

    public static final int BUFFER_SIZE = 1024;

    public Path randomPath(Path base) {
        return Paths.get(base.toAbsolutePath().toString(), UUID.randomUUID().toString());
    }
    public Path randomDirectory() throws IOException {
        return randomDirectory(Paths.get("/tmp/massdriver/tests/"));
    }
    public Path randomDirectory(Path base) throws IOException {
        Path randomDirectory = randomPath(base);
        Files.createDirectories(randomDirectory);
        return randomDirectory;
    }

    public Path setRandomFileSize(long fileSizeBytes) throws IOException {
        randomFile = randomFile(randomDirectory, fileSizeBytes);
        return randomFile;
    }

    public Path randomFile(Path directory) throws IOException {
        return randomFile(directory, 1011*998*51);
    }

    public Path randomFile(Path directory, long fileSizeBytes) throws IOException {
        Path randomFile;
        if (Files.isRegularFile(directory)) { //.toFile().isFile()) {
            randomFile = directory;
        } else {
            randomFile = randomPath(directory);
        }

        return randomContent(randomFile, fileSizeBytes);
    }


    public Path randomContent(Path randomFile, long fileSizeBytes) throws IOException {
        logger.info("Making random file "+randomFile.toAbsolutePath().toString()+" of size " + hrbc(fileSizeBytes));
        Random random = new Random();

        long remaining = fileSizeBytes;
        while (remaining > 0) {
            byte[] buffer = new byte[BUFFER_SIZE];
            random.nextBytes(buffer);
            Files.write(randomFile, buffer, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            remaining = remaining - buffer.length;
        }
        String md5 = CryptUtils.hash("MD5", randomFile);
        logger.info("File created: (MD5) " + md5);
        return randomFile;
    }
}
