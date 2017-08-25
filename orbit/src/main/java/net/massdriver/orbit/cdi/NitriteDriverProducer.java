package net.massdriver.orbit.cdi;

import com.nsnc.massdriver.nitrite.NitriteDriver;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.util.logging.Logger;


@ApplicationScoped
public class NitriteDriverProducer {
  protected Logger logger = Logger.getLogger(getClass().getName());


  private NitriteDriver nitriteDriver;

  @Produces @Default @ApplicationScoped
  public NitriteDriver makeReflections() throws IOException {
    return new NitriteDriver("/tmp/massdriver/seed/");
  }

}