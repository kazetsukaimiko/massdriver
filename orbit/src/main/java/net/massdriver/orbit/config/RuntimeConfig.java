package net.massdriver.orbit.config;

public class RuntimeConfig {

  public static String runtimeProperty(String systemProperty, String defaultValue) {
    String systemPropertyValue = System.getProperty(systemProperty);
    if (systemPropertyValue != null) {
      return systemPropertyValue;
    } return defaultValue;
  }
}
