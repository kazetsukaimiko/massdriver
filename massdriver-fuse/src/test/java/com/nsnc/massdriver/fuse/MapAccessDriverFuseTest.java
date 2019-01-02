package com.nsnc.massdriver.fuse;

import java.io.IOException;

import com.nsnc.massdriver.driver.MapAccessDriver;

public class MapAccessDriverFuseTest extends DriverFuseTest<MapAccessDriver> {

	@Override
	public MapAccessDriver makeDriver() throws IOException {
		return new MapAccessDriver();
	}
}
