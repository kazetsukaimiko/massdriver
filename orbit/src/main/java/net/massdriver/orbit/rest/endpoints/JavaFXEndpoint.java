package net.massdriver.orbit.rest.endpoints;

import java.util.logging.Logger;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import javax.inject.Inject;
import javax.servlet.ServletContext;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.mongodb.morphia.Datastore;


import net.massdriver.orbit.config.Configuration;


@ApplicationScoped
@Path("/javafx")
public class JavaFXEndpoint {

  protected Logger logger = Logger.getLogger(getClass().getName());
  
  @Inject 
  private Configuration config;
  
  @Inject
  private Datastore datastore;

  @Context 
  private ServletContext context; 
  
/*
  @Inject[A @Any
  private Instance<JavaFXTrayIconSample> samples;

  @Inject
  private TrayIcon trayIcon;
  

  @GET
  @Path("/test")
  @Produces("application/json")
  public String getConfig() throws Exception {
    trayIcon.demo();
    return "OK...";
  }

  @GET
  @Path("/count")
  @Produces("application/json")
  public Integer getCount() throws Exception {
    return trayIcon.getIncrement();
  }
  
  */
  @GET
  @Path("/context/{item:.+}")
  //@Produces("application/octet-stream")
  public Response getCcontextItem(@PathParam("item") String item) throws Exception {
    InputStream is = context.getResourceAsStream(item);
    if (is == null) {
      return Response.ok().entity("No such item.").type("application/json").build();
    } return Response.ok().entity(is).type("application/octet-stream").build();
  }

  @GET
  @Path("/classpath")
  @Produces("application/json")
  public List<URL> getClasspath() throws Exception {
    ClassLoader cl = ClassLoader.getSystemClassLoader();
    URL[] urls = ((URLClassLoader)cl).getURLs();
    return Arrays.asList(urls);
  }

  @GET
  @Path("/resources")
  @Produces("application/json")
  public Map<String, List<URL>> getResourceRoot() throws Exception {
    return getResources("");
  }

  @GET
  @Path("/resources/{rstring:.*}")
  @Produces("application/json")
  public Map<String, List<URL>> getResources(@PathParam("rstring") String rString) throws Exception {
    logger.info("Getting resources of:");
    logger.info(rString);
    Map<String, List<URL>> resourcesInLoaders = new HashMap<String, List<URL>>();
    ClassLoader cl = getClass().getClassLoader();
    Integer i = new Integer(0);
    resourcesInLoaders.put("CL"+String.valueOf(i), Collections.list(cl.getResources(rString)));
    while (cl.getParent() != null) {
      i++;
      cl = cl.getParent();
      resourcesInLoaders.put("CL"+String.valueOf(i), Collections.list(cl.getResources(rString)));
      if (i>10) {
        break;
      }
    } return resourcesInLoaders;
  }

  @GET
  @Path("/resource/{resource:.+}")
  public InputStream getStream(@PathParam("resource") String resourceName) {
    InputStream inputStream = 
      getClass().getClassLoader().getResourceAsStream(resourceName);
    return inputStream;
    //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream ));
  }

  @GET
  @Path("/image")
  @Produces("image/png")
  public InputStream getIcon() {
    InputStream inputStream = 
      getClass().getClassLoader().getResourceAsStream("orbit.png");
    return inputStream;
    //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream ));
  }
}