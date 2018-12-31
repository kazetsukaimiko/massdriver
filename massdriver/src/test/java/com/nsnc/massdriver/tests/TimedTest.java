package com.nsnc.massdriver.tests;

import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Created by luna on 8/7/17.
 */
public class TimedTest {
    private long startMillis = 0;
    protected final Logger logger = Logger.getLogger(getClass().getName());

    @BeforeEach
    public void setStartMillis() {
        startMillis = System.currentTimeMillis();
    }

    @AfterEach
    public void calculateTestTime() {
        long finishMillis = System.currentTimeMillis();
        long time = finishMillis-startMillis;
        logger.info("Test time: "+time+"ms");
    }
}
