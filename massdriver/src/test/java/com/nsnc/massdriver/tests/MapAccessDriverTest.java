package com.nsnc.massdriver.tests;

import com.nsnc.massdriver.driver.Driver;
import com.nsnc.massdriver.driver.MapAccessDriver;

public class MapAccessDriverTest extends DriverTest {
    @Override
    public Driver make() {
        return new MapAccessDriver();
    }
}