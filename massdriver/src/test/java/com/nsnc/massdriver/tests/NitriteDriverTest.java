package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.nitrite.NitriteDriver;
import org.dizitart.no2.Nitrite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NitriteDriverTest extends DriverTest {
    private static final Path massdriverDirectory = rootDirectory;
    private static final Path indexDirectory = Paths.get(
            massdriverDirectory.toAbsolutePath().toString(),
            "toIndex"
    );
    private static final Path fragmentsDirectory = Paths.get(
            massdriverDirectory.toAbsolutePath().toString(),
            "fragments"
    );
    private static final Path databaseFile = Paths.get(
            massdriverDirectory.toAbsolutePath().toString(),
            "massdriver.db"
    );

    private Nitrite nitrite;
    private NitriteDriver driver;

    @Override
    public Driver make() throws IOException {
        Files.deleteIfExists(databaseFile);
        nitrite = Nitrite.builder()
                .filePath(databaseFile.toFile())
                .openOrCreate();

        return new NitriteDriver(fragmentsDirectory, nitrite);
    }


}
