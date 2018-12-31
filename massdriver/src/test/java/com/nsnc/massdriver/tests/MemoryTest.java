package com.nsnc.massdriver.tests;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

/**
 * Created by luna on 8/7/17.
 */
public abstract class MemoryTest extends TimedTest {

    private long usedMemory = -1;

    @BeforeEach
    public void initMemory(TestInfo testInfo) {
        System.gc();
        usedMemory = getCurrentUsedMemory();
        logger.info("Memory going into "+getMethodName(testInfo)+":" + hrbc(usedMemory));
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

    @AfterEach
    public void exitMemory(TestInfo testInfo) {
        long usedMemory = getCurrentUsedMemory();
        System.gc();
        long postGCUsedMemory = getCurrentUsedMemory();
        logger.info("Memory going out of "+getMethodName(testInfo)+ ": " +
                hrbc(usedMemory) + " (" + ((usedMemory>this.usedMemory)? "+":"-") + hrbc(usedMemory-this.usedMemory) + ") / " +
                hrbc(postGCUsedMemory) + " (" + ((postGCUsedMemory>this.usedMemory)? "+":"-") + hrbc(postGCUsedMemory-this.usedMemory) + ")"
        );
        logger.info("Max Memory/Total Memory ("+getMethodName(testInfo)+", Previous): " + hrbc(getTotalMemory()) + "/" + hrbc(getMaxMemory()));
    }




    private String getMethodName(TestInfo testInfo) {
        return testInfo.getTestMethod().map(Method::getName).orElse("UNKNOWN_METHOD");
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
