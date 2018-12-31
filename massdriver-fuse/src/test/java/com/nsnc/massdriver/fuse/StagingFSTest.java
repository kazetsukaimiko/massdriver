package com.nsnc.massdriver.fuse;

import com.nsnc.massdriver.fuse.staging.StagingFS;
import ru.serce.jnrfuse.Mountable;

public class StagingFSTest extends FuseTest {

    @Override
    public Mountable getMountable() {
        return new StagingFS();
    }



}
