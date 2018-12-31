package com.nsnc.massdriver.tests;

import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Created by luna on 8/7/17.
 */
public class TimedTest extends BaseTest {
    private long startMillis = 0;

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
