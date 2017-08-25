package net.massdriver.orbit.entity;

import java.util.List;
import java.util.ArrayList;
import com.sun.jna.platform.unix.X11;

public class X11WindowInfo {

  private Long windowId;
  public Long getWindowId() {
    return windowId;
  }
  public void setWindowId(Long aWindowId) {
    windowId = aWindowId;
  }
  public X11WindowInfo windowId(Long aWindowId) {
    setWindowId(aWindowId); return this;
  }

  private String windowName;
  public String getWindowName() {
    return windowName;
  }
  public void setWindowName(String aWindowName) {
    windowName = aWindowName;
  }
  public X11WindowInfo windowName(String aWindowName) {
    setWindowName(aWindowName); return this;
  }

  private X11.XWindowAttributes windowAttributes;
  public X11.XWindowAttributes getWindowAttributes() {
    return windowAttributes;
  }
  public void setWindowAttributes(X11.XWindowAttributes aWindowAttributes) {
    windowAttributes = aWindowAttributes;
  }
  public X11WindowInfo windowAttributes(X11.XWindowAttributes aWindowAttributes) {
    setWindowAttributes(aWindowAttributes); return this;
  }

  private List<X11WindowInfo> children = new ArrayList<X11WindowInfo>();
  public List<X11WindowInfo> getChildren() {
    return children;
  }
  public void setChildren(List<X11WindowInfo> someChildren) {
    children = someChildren;
  }
  public X11WindowInfo children(List<X11WindowInfo> someChildren) {
    setChildren(someChildren); return this;
  }

  private List<Long> parents = new ArrayList<Long>();
  public List<Long> getParents() {
    return parents;
  }
  public void setParents(List<Long> someParents) {
    parents = someParents;
  }
  public X11WindowInfo parents(List<Long> someParents) {
    setParents(someParents); return this;
  }

}