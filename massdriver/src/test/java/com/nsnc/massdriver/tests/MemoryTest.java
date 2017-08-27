package com.nsnc.massdriver.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.util.logging.Logger;

/**
 * Created by luna on 8/7/17.
 */
public abstract class MemoryTest extends TimedTest {

    private long usedMemory = -1;

    @Rule
    public TestName name = new TestName();

    @Before
    public void initMemory() {
        System.gc();
        usedMemory = getCurrentUsedMemory();
        logger.info("Memory going into "+getMethodName()+":" + hrbc(usedMemory));
        logger.info("Max Memory/Total Memory: " + hrbc(getTotalMemory()) + "/" + hrbc(getMaxMemory()));
    }

    private long getCurrentUsedMemory() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    private long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    private long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    @After
    public void exitMemory() {
        long usedMemory = getCurrentUsedMemory();
        System.gc();
        long postGCUsedMemory = getCurrentUsedMemory();
        logger.info("Memory going out of "+getMethodName()+ ": " +
                hrbc(usedMemory) + " (" + ((usedMemory>this.usedMemory)? "+":"-") + hrbc(usedMemory-this.usedMemory) + ") / " +
                hrbc(postGCUsedMemory) + " (" + ((postGCUsedMemory>this.usedMemory)? "+":"-") + hrbc(postGCUsedMemory-this.usedMemory) + ")"
        );
        logger.info("Max Memory/Total Memory ("+getMethodName()+", Previous): " + hrbc(getTotalMemory()) + "/" + hrbc(getMaxMemory()));
    }




    private String getMethodName() {
        return name.getMethodName();
    }

    public static String hrbc(long bytes) {
        return humanReadableByteCount(Math.abs(bytes), false);
    }
    // Author: aioobe
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
