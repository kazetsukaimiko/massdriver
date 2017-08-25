package net.massdriver.orbit.servlet.listener;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.massdriver.orbit.util.TrayIcon;
import net.massdriver.orbit.config.Configuration;
import net.massdriver.orbit.rest.endpoints.X11Endpoint;

@WebListener
public class StartupListener implements ServletContextListener {

  protected Logger logger = Logger.getLogger(getClass().getName());

  private TrayIcon trayIcon;
  private final java.awt.Font defaultFont = java.awt.Font.decode(null).deriveFont(java.awt.Font.PLAIN, 20);
  private final java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);

  @Inject 
  private Configuration config;
  
  @Inject
  private X11Endpoint x11Endpoint;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    //listProperties();
    System.out.println("---- initialize servlet context -----");
    ServletContext context = servletContextEvent.getServletContext();
    try {
      trayIcon = new TrayIcon()
        //.iconImage("http://localhost:8080/orbit.png")
        .iconImageFromStream(context.getResourceAsStream(config.getIconImage()))
        .action("SNOWFLAKE!", event -> {}, boldFont) //, boldFont)
        .action("Tray Icon Size", event -> {
          //System.exit(0);
          trayIcon.dumpTrayImageSize();
        })
        .action("Exit", event -> {
          System.exit(0);
        }).build();

      trayIcon.iconSize(x11Endpoint.getLinuxIconSize());

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Cannot spawn Tray Icon: " , e);
    }
    // add initialization code here
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    System.out.println("---- destroying servlet context -----");
    // clean up resources
    if (trayIcon != null) {
      trayIcon.destroy();
    }
    System.out.println("---- destroyed TrayIcon -----");
  }
  
  public void listProperties() {
    System.out.println("---- SYSTEM PROPERTIES -----");
    Properties props = System.getProperties();
    props.list(System.out);
    System.out.println("---- END SYSTEM PROPERTIES -----");
  }
}