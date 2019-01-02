package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.driver.MapAccessDriver;

public class MapAccessDriverTest extends DriverTest<MapAccessDriver> {
    @Override
    public final MapAccessDriver makeDriver() {
        return new MapAccessDriver();
    }
}
