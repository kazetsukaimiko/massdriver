package net.massdriver.orbit.javafx;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Default;
import javax.enterprise.context.ApplicationScoped;

import org.reflections.Reflections;
import org.reflections.util.FilterBuilder;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.ResourcesScanner;


@ApplicationScoped
public class JavaFXTrayIconSampleProducer {
  protected Logger logger = Logger.getLogger(getClass().getName());


  @Produces @Default
  public JavaFXTrayIconSample makeJavaFXTrayIconSample() {
    logger.info("Constructing Reflections...");
    return new JavaFXTrayIconSample();
  }

}