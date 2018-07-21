package com.nsnc.massdriver.fuse;

import jnr.ffi.Platform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.serce.jnrfuse.Mountable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by luna on 8/6/17.
 */
public class HelloFuseTest extends FuseTest {

    @Override
    public Mountable getMountable() {
        return new HelloFuse();
    }
}
