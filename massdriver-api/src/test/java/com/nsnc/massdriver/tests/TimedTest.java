package com.nsnc.massdriver.tests;

import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.nsnc.massdriver.util.Bench;

/**
 * Created by luna on 8/7/17.
 */
public abstract class TimedTest extends BaseTest {
    private long startMillis = 0;
    public Bench bench = new Bench(Logger.getLogger(getClass().getName()));

    @BeforeEach
    public void setStartMillis() {
        startMillis = System.currentTimeMillis();
    }

    @AfterEach
    public void calculateTestTime() {
        long finishMillis = System.currentTimeMillis();
        long time = finishMillis-startMillis;
        LOGGER.info("Test time: "+time+"ms");
    }
}
