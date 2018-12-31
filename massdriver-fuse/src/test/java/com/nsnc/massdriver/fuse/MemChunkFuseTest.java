package com.nsnc.massdriver.fuse;

import com.nsnc.massdriver.driver.Driver;

import ru.serce.jnrfuse.Mountable;

public class MemChunkFuseTest extends FuseTest {
	@Override
	public Mountable getMountable() {
		return new MemChunkFuse();
	}

	@Override
	public Driver getDriver() {
		return null;
	}
}
