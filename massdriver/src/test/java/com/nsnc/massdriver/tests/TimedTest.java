package com.nsnc.massdriver.tests;

import org.junit.After;
import org.junit.Before;

import java.util.logging.Logger;

/**
 * Created by luna on 8/7/17.
 */
public class TimedTest {
    private long startMillis = 0;
    protected final Logger logger = Logger.getLogger(getClass().getName());

    @Before
    public void setStartMillis() {
        startMillis = System.currentTimeMillis();
    }

    @After
    public void calculateTestTime() {
        long finishMillis = System.currentTimeMillis();
        long time = finishMillis-startMillis;
        logger.info("Test time: "+time+"ms");
    }
}
