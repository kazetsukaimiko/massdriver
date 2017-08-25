package net.massdriver.orbit.cdi;

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
public class ReflectionsProducer {
  protected Logger logger = Logger.getLogger(getClass().getName());


  @Produces @Default @ApplicationScoped
  public Reflections makeReflections() {
    logger.info("Constructing Reflections...");
    return new Reflections(new ConfigurationBuilder()
      .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
      .setUrls(ClasspathHelper.forClassLoader(new ClassLoader[] { ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader()}))
      .filterInputsBy(new FilterBuilder().include(".*"))
    );
  }

}