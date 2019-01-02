package com.nsnc.massdriver.index;

import com.nsnc.massdriver.asset.Asset;
import com.nsnc.massdriver.asset.FileAsset;
import com.nsnc.massdriver.driver.Driver;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RecursingIndexer extends SimpleFileVisitor<Path> {
    private static final Logger LOGGER = Logger.getLogger(RecursingIndexer.class.getName());
    private final List<Asset> indexed = new ArrayList<>();
    private final List<Path> filesVisited = new ArrayList<>();
    private final List<Path> directoriesVisited = new ArrayList<>();
    private final Driver driver;

    public RecursingIndexer(Driver driver) {
        this.driver = driver;
    }

    public List<Asset> getIndexed() {
        return indexed;
    }

    public List<Path> getFilesVisited() {
        return filesVisited;
    }

    public List<Path> getDirectoriesVisited() {
        return directoriesVisited;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        //indexed.add(driver);
        directoriesVisited.add(dir);
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileAsset fileAsset = new FileAsset(file);
        LOGGER.info("Adding Asset: " + file.toAbsolutePath().toString());
        filesVisited.add(file);
        driver.persistAsset(fileAsset);
        return super.visitFile(file, attrs);
    }
}
