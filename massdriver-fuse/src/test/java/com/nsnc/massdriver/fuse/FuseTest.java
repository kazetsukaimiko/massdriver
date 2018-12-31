package com.nsnc.massdriver.fuse;

import com.nsnc.massdriver.tests.FileSystemTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.serce.jnrfuse.Mountable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by luna on 8/7/17.
 */
public abstract class FuseTest extends FileSystemTest {

    public abstract Mountable getMountable();

    private static Path mountPoint = Paths.get(rootDirectory.toAbsolutePath().toString(), "/fuse");
    protected Mountable mountable;

    @Before
    public void setupFuse() throws IOException {
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
    }

    @After
    public void unmountFuse() {
        mountable.umount();
        mountable = null;
    }
}
