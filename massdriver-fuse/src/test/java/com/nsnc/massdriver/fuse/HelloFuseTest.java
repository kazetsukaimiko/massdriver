package com.nsnc.massdriver.fuse;

import com.nsnc.massdriver.driver.Driver;

import ru.serce.jnrfuse.Mountable;

/**
 * Created by luna on 8/6/17.
 */
public class HelloFuseTest extends FuseTest {

    @Override
    public Mountable getMountable() {
        return new HelloFuse();
    }

    @Override
    public Driver getDriver() {
        return null;
    }
}
