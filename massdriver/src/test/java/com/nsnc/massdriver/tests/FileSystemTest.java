package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.crypt.CryptUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by luna on 8/7/17.
 */
public abstract class FileSystemTest extends MemoryTest {

    protected static Path rootDirectory = Paths.get("/tmp/massdriver/");

    protected static Path randomTree = null;
    protected static Path randomEmptyDirectory = null;
    protected static Path randomDirectory = null;
    protected static Path randomFile = null;

    @BeforeClass
    public static void createRandomFiles() throws IOException {
        LOGGER.info("Random Empty Directory");
        randomEmptyDirectory = randomDirectory();
        LOGGER.info("Random Directory");
        randomDirectory = randomDirectory();
        LOGGER.info("Random File");
        randomFile = randomFile(randomDirectory);
        LOGGER.info("Random Tree");
        randomTree = makeRandomTree();
    }


    public static Path makeRandomTree() throws IOException {
        randomTree = Paths.get(rootDirectory.toString(), "/tree");
        LOGGER.info("Clearing: " + randomTree.toString());
        deleteDirectory(randomTree);
        randomDirectories(randomTree, 3);
        return randomTree;
    }

    @After
    public void cleanupRandomFiles() throws IOException {
        //deleteDirectory(randomDirectory);
        //deleteDirectory(randomEmptyDirectory);
    }


    protected static Path getSeedDirectory() throws IOException {
        return Files.createDirectories(Paths.get(rootDirectory.toString(),"/seed/"));
    }

    private static void deleteDirectory(Path directory) throws IOException {
        if (directory.toFile().exists()) {
            Files.walk(directory)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    //.peek(System.out::println)
                    .forEach(File::delete);
        }
    }

    public static final int BUFFER_SIZE = 1024;

    public static Path randomPath(Path base) {
        Path path = null;
        while (path == null || path.toFile().exists()) {
            path = Paths.get(base.toAbsolutePath().toString(), randomName());
        }
        return path;
    }
    public static Path randomDirectory() throws IOException {
        Path randomDirectory = Paths.get(rootDirectory.toString(), "/tests/");
        return randomDirectory(randomDirectory);
    }
    public static Path randomDirectory(Path base) throws IOException {
        Path randomDirectory = randomPath(base);
        deleteDirectory(randomDirectory);
        Files.createDirectories(randomDirectory);
        return randomDirectory;
    }
    public static List<Path> randomDirectories(Path base, int max) throws IOException {
        LOGGER.info("Populating: " + base.toAbsolutePath().toString() + " w/max of " + max);
        List<Path> randomPaths = new ArrayList<>();
        if (max > 0) {
            for (int i=0;i<RANDOM.nextInt(max)+1;i++) {
                Path subdirectory = randomDirectory(base);
                randomPaths.addAll(randomFiles(subdirectory, max));
                randomPaths.addAll(randomDirectories(subdirectory, max-1));
            }
        }
        return randomPaths;
    }

    public static List<Path> randomFiles(Path base, int max) throws IOException {
        List<Path> randomFiles = new ArrayList<>();
        if (max > 0) {
            for (int i = 0; i < RANDOM.nextInt(max)+1; i++) {
                randomFiles.add(randomFile(base));
            }
        }
        return randomFiles;
    }

    public static Path setRandomFileSize(long fileSizeBytes) throws IOException {
        randomFile = randomFile(randomDirectory, fileSizeBytes);
        return randomFile;
    }

    public static int randomSize() {
        int multiplier = 5;
        int variation = 1024-900;
        return (1024-RANDOM.nextInt(variation)) * (1024-RANDOM.nextInt(variation)) * multiplier;
    }

    public static Path randomFile(Path directory) throws IOException {
        return randomFile(directory, randomSize());
    }

    public static Path randomFile(Path directory, long fileSizeBytes) throws IOException {
        Path randomFile;
        if (Files.isRegularFile(directory)) { //.toFile().isFile()) {
            randomFile = directory;
        } else {
            randomFile = randomPath(directory);
        }

        return randomContent(randomFile, fileSizeBytes);
    }


    public static Path randomContent(Path randomFile, long fileSizeBytes) throws IOException {
        LOGGER.info("Making random file "+randomFile.toAbsolutePath().toString()+" of size " + hrbc(fileSizeBytes));
        Random random = new Random();

        long remaining = fileSizeBytes;
        while (remaining > 0) {
            byte[] buffer = new byte[BUFFER_SIZE];
            random.nextBytes(buffer);
            Files.write(randomFile, buffer, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            remaining = remaining - buffer.length;
        }
        String md5 = CryptUtils.hash("MD5", randomFile);
        LOGGER.info("File created: (MD5) " + md5);
        return randomFile;
    }
}
