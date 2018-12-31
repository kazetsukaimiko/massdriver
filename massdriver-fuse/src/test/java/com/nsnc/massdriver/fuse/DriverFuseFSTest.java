package com.nsnc.massdriver.fuse;

import java.net.UnknownHostException;

import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.driver.MapAccessDriver;
import com.nsnc.massdriver.fuse.easy.MassDriverFuseFS;

import ru.serce.jnrfuse.Mountable;

public class DriverFuseFSTest extends FuseTest {

	private Driver driver = new MapAccessDriver();

	@Override
	public Mountable getMountable() {
		try {
			return new MassDriverFuseFS(getDriver());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Driver getDriver() {
		return driver;
	}
}
