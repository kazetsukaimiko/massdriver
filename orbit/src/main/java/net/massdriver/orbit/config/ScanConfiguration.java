package net.massdriver.orbit.config;

/**
 * Created by luna on 7/29/17.
 */
public class ScanConfiguration extends RuntimeConfig {

    public static final String SCANDIR_PROPERTY = "scanDirectory";
    private String directory = runtimeProperty(SCANDIR_PROPERTY, System.getProperty("user.home"));

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
