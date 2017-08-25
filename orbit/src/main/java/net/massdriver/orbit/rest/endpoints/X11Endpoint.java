package net.massdriver.orbit.rest.endpoints;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/*
import java.util.HashMap;
import java.util.Collections;
import java.net.URL;
import java.net.URLClassLoader;


import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.ws.rs.core.Response;

import org.mongodb.morphia.Datastore;


import MongoDBConfig;

*/
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import javax.enterprise.context.ApplicationScoped;


import com.sun.jna.platform.unix.X11;
//import com.sun.jna.platform.unix.X11.*;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.IntByReference;

import net.massdriver.orbit.config.Configuration;
import net.massdriver.orbit.entity.X11WindowInfo;

@ApplicationScoped
@Path("/x11")
public class X11Endpoint {

  protected Logger logger = Logger.getLogger(getClass().getName());
  
  @Inject
  private Configuration config;
  
  @Context 
  private ServletContext context; 

  @GET
  @Path("/image")
  @Produces("image/png")
  public InputStream getIcon() {
    InputStream inputStream = 
      getClass().getClassLoader().getResourceAsStream("orbit.png");
    return inputStream;
    //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream ));
  }

  public X11 getX11() {
    return X11.INSTANCE;
  }
  

  // int XQueryTree(Display display, Window window, WindowByReference root, WindowByReference parent, PointerByReference children, IntByReference childCount);



  @GET
  @Path("/windows")
  @Produces("application/json")
  public List<X11WindowInfo> getWindowInfo() {
    List<X11WindowInfo> infoList = new ArrayList<X11WindowInfo>();
    X11 x11 = X11.INSTANCE;
    X11.Display display = null;
    try {
      display= x11.XOpenDisplay(null);
      X11.Window rootWindow = x11.XDefaultRootWindow(display);
      Long rootWindowId = rootWindow.longValue();
      infoList = getWindowInfoRecursive(x11, display, rootWindowId, new ArrayList<Long>());
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Cannot get Window Attrs:" , e);
    } finally {
      if (display != null) {
        x11.XCloseDisplay(display);
      }
    } return infoList;
  }
  
  public X11WindowInfo getWindowInfo(X11 x11, X11.Display display, Long windowId) {
    X11.Window window = new X11.Window(windowId);
    PointerByReference namePointerRef = new PointerByReference();
    x11.XFetchName(display, window, namePointerRef);

    String windowName = "";
    if (namePointerRef != null && namePointerRef.getValue() != null) {
      windowName = namePointerRef.getValue().getString(0);
    }

    X11.XWindowAttributes windowAttributes = new X11.XWindowAttributes();
    x11.XGetWindowAttributes(display, window, windowAttributes);

    return new X11WindowInfo()
      .windowId(windowId)
      .windowName(windowName)
      .windowAttributes(windowAttributes);
  }


  public List<X11WindowInfo> getWindowInfoRecursive(X11 x11, X11.Display display, Long parentWindowId, List<Long> parentHeirarchy) {
    List<X11WindowInfo> infoList = new ArrayList<X11WindowInfo>();
    X11.WindowByReference rootReference = new X11.WindowByReference();
    X11.WindowByReference parent = new X11.WindowByReference();
    PointerByReference children = new PointerByReference();
    IntByReference childCount = new IntByReference();
    X11.Window parentWindow = new X11.Window(parentWindowId);
    x11.XQueryTree(display, parentWindow, rootReference, parent, children, childCount);
    if (children != null && children.getValue() != null && childCount.getValue() > 0) {
      long[] childIds = children.getValue().getLongArray(0, childCount.getValue());
      for (long childId : childIds) {
        List<Long> parentsAbove = new ArrayList<Long>();
        parentsAbove.addAll(parentHeirarchy); parentsAbove.add(childId);
        X11WindowInfo info = getWindowInfo(x11, display, childId).parents(parentHeirarchy).children(getWindowInfoRecursive(x11, display, childId, parentsAbove));
        infoList.add(info);
      }
    } return infoList;
  }

  @GET
  @Path("/windows/names")
  @Produces("application/json")
  public Map<Long, String> getWindowNamesLinux() {
    Map<Long, String> nameMap = new LinkedHashMap<Long, String>();
    try {
      X11 x11 = X11.INSTANCE;
      X11.Display display= x11.XOpenDisplay(null);
      for(Long wid : getWindowIdsLinux()) {
        X11.Window window = new X11.Window(wid);
        PointerByReference namePointerRef = new PointerByReference();
        x11.XFetchName(display, window, namePointerRef);
        if (namePointerRef != null && namePointerRef.getValue() != null) {
          String windowName = namePointerRef.getValue().getString(0);
          nameMap.put(wid,windowName);
        } else {
          nameMap.put(wid, null);
        }
      }
      
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Cannot get Window Attrs:" , e);
    } return nameMap;
  }

  @GET
  @Path("/awtIconSize")
  @Produces("application/json")
  public java.awt.Dimension getLinuxIconSize() {
    try {
      X11 x11 = X11.INSTANCE;
      X11.Display display= x11.XOpenDisplay(null);
      for(Long wid : getWindowIdsLinux()) {
        X11.Window window = new X11.Window(wid);
        PointerByReference namePointerRef = new PointerByReference();
        x11.XFetchName(display, window, namePointerRef);
        if (namePointerRef != null && namePointerRef.getValue() != null) {
          String windowName = namePointerRef.getValue().getString(0);
          logger.info("Window Name: " + windowName);
          if (Objects.equals("sun-awt-X11-XCanvasPeer", windowName)) {
            X11.XWindowAttributes xwa = new X11.XWindowAttributes();
            x11.XGetWindowAttributes(display, window, xwa);
            logger.info("Icon Size: "+String.valueOf(xwa.width)+"x"+String.valueOf(xwa.height));
            return new java.awt.Dimension(xwa.width, xwa.height);
          }
        }
      }
      
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Cannot get IconSize:" , e);
    } logger.info("Couldn't find Window/Size."); return null;
  }

  @GET
  @Path("/windows/attrs")
  @Produces("application/json")
  public Map<Long, X11.XWindowAttributes> getWindowAttrsLinux() {
    Map<Long, X11.XWindowAttributes> attrMap = new LinkedHashMap<Long, X11.XWindowAttributes>();
    try {
      X11 x11 = X11.INSTANCE;
      X11.Display display= x11.XOpenDisplay(null);
      for(Long wid : getWindowIdsLinux()) {
        X11.Window window = new X11.Window(wid);
        X11.XWindowAttributes xwa = new X11.XWindowAttributes();
        x11.XGetWindowAttributes(display, window, xwa);
        attrMap.put(wid,xwa);
      }
      
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Cannot get Window Attrs:" , e);
    } return attrMap;
  }



  public List<Long> getChildWindowsLinux(Long parentWindowId) {
    List<Long> widsList = new ArrayList<Long>();

    X11 x11 = X11.INSTANCE;
    X11.Display display = null;
    try {
      display = x11.XOpenDisplay(null);
      X11.WindowByReference rootReference = new X11.WindowByReference();
      X11.WindowByReference parent = new X11.WindowByReference();
      PointerByReference children = new PointerByReference();
      IntByReference childCount = new IntByReference();
      X11.Window parentWindow = new X11.Window(parentWindowId);
      x11.XQueryTree(display, parentWindow, rootReference, parent, children, childCount);
      if (children != null && children.getValue() != null && childCount.getValue() > 0) {
        long[] wids = children.getValue().getLongArray(0, childCount.getValue());
        for (long wid : wids) {
          widsList.add(new Long(wid));
          widsList.addAll(getChildWindowsLinux(wid));
        }
      }
    } finally {
      if (display != null) {
        x11.XCloseDisplay(display);
      }
    } return widsList;

  }



  @GET
  @Path("/windows/ids")
  @Produces("application/json")
  public List<Long> getWindowIdsLinux() {
    List<Long> widsList = new ArrayList<Long>();
  
    X11 x11 = X11.INSTANCE;
    X11.Display display = null;
    try {
      display = x11.XOpenDisplay(null);
      X11.Window rootWindow = x11.XDefaultRootWindow(display);
      widsList.addAll(getChildWindowsLinux(rootWindow.longValue()));
    } finally {
      if (display != null) {
        x11.XCloseDisplay(display);
      }
    } return widsList;
  }
/* 
  private static void setFullScreenWindowLinux(Window window, boolean fullScreen) {
    X11 x11 = X11.INSTANCE;
    Display display = null;
    try {
      display = x11.XOpenDisplay(null);
      long windowID = Native.getWindowID(window);

      // Event set up
      XEvent xEvent = new XEvent();
      xEvent.setType(XClientMessageEvent.class);

      xEvent.xclient.type = X11.ClientMessage;
      xEvent.xclient.message_type = x11.XInternAtom(display, "_NET_WM_STATE", false);
      xEvent.xclient.format = 32;
      xEvent.xclient.window = new com.sun.jna.platform.unix.X11.Window(windowID);
      xEvent.xclient.send_event = 1;
      xEvent.xclient.serial = new NativeLong(0L);

      xEvent.xclient.data.setType(NativeLong[].class);
      xEvent.xclient.data.l[0] = new NativeLong(fullScreen ? _NET_WM_STATE_ADD : _NET_WM_STATE_REMOVE);
      xEvent.xclient.data.l[1] = x11.XInternAtom(display, "_NET_WM_STATE_FULLSCREEN", false);
      xEvent.xclient.data.l[2] = new NativeLong(0L);
      xEvent.xclient.data.l[3] = new NativeLong(0L);
      xEvent.xclient.data.l[4] = new NativeLong(0L);

      // Make it so
      NativeLong mask = new NativeLong(X11.SubstructureRedirectMask | X11.SubstructureNotifyMask);
      x11.XSendEvent(display, x11.XDefaultRootWindow(display), 0, mask, xEvent);
      x11.XFlush(display);
    } finally {
      if (display != null) {
        x11.XCloseDisplay(display);
      }
    }
  }
 */



  
}