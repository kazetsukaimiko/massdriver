package com.nsnc.massdriver.fuse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nsnc.massdriver.tests.DriverTest;

import ru.serce.jnrfuse.Mountable;

/**
 * Created by luna on 8/7/17.
 */
public abstract class FuseTest extends DriverTest {

    public abstract Mountable getMountable();

    private static Path mountPoint = Paths.get(rootDirectory.toAbsolutePath().toString(), "/fuse");
    protected Mountable mountable;

    @BeforeEach
    public void setupFuse() throws IOException {
		System.out.println("FuseTest.before");
        deleteDirectory(mountPoint);
        Files.createDirectories(mountPoint);
        mountable = getMountable();
        mountable.mount(mountPoint, false, false);
    }

    @Test
    public void ls() throws IOException, InterruptedException {
        Files.walk(mountPoint)
                .map(path -> path.toAbsolutePath().toString())
                .forEach(System.out::println);
        System.out.println("Done walking");
    }

    @AfterEach
    public void unmountFuse() {
        mountable.umount();
        mountable = null;
    }
}
