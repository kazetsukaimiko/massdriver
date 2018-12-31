package com.nsnc.massdriver.fuse;

import java.net.UnknownHostException;

import com.nsnc.massdriver.fuse.easy.MassDriverFuseFS;

import ru.serce.jnrfuse.Mountable;

public class DriverFuseFSTest extends FuseTest {
	@Override
	public Mountable getMountable() {
		try {
			return new MassDriverFuseFS(getDriver());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
