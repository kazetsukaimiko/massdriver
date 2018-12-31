package com.nsnc.massdriver.fuse;

import java.io.IOException;
import java.nio.file.Files;

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

    protected Mountable mountable;

    @BeforeEach
    public void setupFuse() {
        System.out.println("FuseTest.before");
        mountable = getMountable();
        mountable.mount(randomEmptyDirectory, false, false);
    }

    @Test
    public void ls() throws IOException, InterruptedException {
        Files.walk(randomEmptyDirectory)
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
