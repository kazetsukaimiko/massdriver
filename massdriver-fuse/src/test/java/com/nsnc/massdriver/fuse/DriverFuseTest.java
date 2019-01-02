package com.nsnc.massdriver.fuse;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.fuse.easy.MassDriverFuseFS;
import com.nsnc.massdriver.tests.DriverTest;

import ru.serce.jnrfuse.Mountable;

/**
 * Created by luna on 8/7/17.
 */
public abstract class DriverFuseTest<T extends Driver> extends DriverTest<T> {

    private Mountable mountable;

    public Mountable getMountable() {
        return mountable;
    }

    protected Path getMountPoint() {
        return Paths.get(rootDirectory.toAbsolutePath().toString(), "/fuse");
    }

    @BeforeEach
    public void setupFuse() throws IOException {
		System.out.println("DriverFuseTest.before");
        deleteDirectory(getMountPoint());
        Files.createDirectories(getMountPoint());
        mountable = new MassDriverFuseFS(getDriver());
        mountable.mount(getMountPoint(), false, false);
    }

    // TODO: Actual Fuse Test Suite Here

    @Test
    public void ls() throws IOException, InterruptedException {
        Files.walk(getMountPoint())
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
