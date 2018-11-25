package com.nsnc.massdriver.tests;

import org.junit.After;
import org.junit.Before;

/**
 * Created by luna on 8/7/17.
 */
public class TimedTest extends BaseTest {
    private long startMillis = 0;

    @Before
    public void setStartMillis() {
        startMillis = System.currentTimeMillis();
    }

    @After
    public void calculateTestTime() {
        long finishMillis = System.currentTimeMillis();
        long time = finishMillis-startMillis;
        LOGGER.info("Test time: "+time+"ms");
    }
}
