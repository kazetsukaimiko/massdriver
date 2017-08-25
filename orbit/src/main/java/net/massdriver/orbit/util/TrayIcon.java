package net.massdriver.orbit.util;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.imageio.ImageIO;

public class TrayIcon { 
  private final Logger logger = Logger.getLogger(getClass().getName());

  private List<java.awt.MenuItem> menuItems = new ArrayList<java.awt.MenuItem>();
  public List<java.awt.MenuItem> getMenuItems() {
    return menuItems;
  }
  public void setMenuItems(List<java.awt.MenuItem> someMenuItems) {
    menuItems = someMenuItems;
  }
  public TrayIcon menuItems(List<java.awt.MenuItem> someMenuItems) {
    setMenuItems(someMenuItems); return this;
  }
  public TrayIcon menuItems() {
    setMenuItems(new ArrayList<java.awt.MenuItem>()); return this;
  }
  public TrayIcon menuItem(java.awt.MenuItem someMenuItem) {
    getMenuItems().add(someMenuItem); return this;
  }
  public TrayIcon action(String title, java.awt.event.ActionListener someAction) {
    java.awt.MenuItem item = new java.awt.MenuItem(title);
    item.addActionListener(someAction);
    return menuItem(item);
  }
  public TrayIcon action(String title, java.awt.event.ActionListener someAction, java.awt.Font someFont) {
    java.awt.MenuItem item = new java.awt.MenuItem(title);
    item.addActionListener(someAction);
    item.setFont(someFont);
    return menuItem(item);
  }
  
  private java.awt.Image iconImage;
  public java.awt.Image getIconImage() {
    return iconImage;
  }


  public void  setIconImage(java.awt.Image anIconImage) {
    iconImage = anIconImage;
  }
  public TrayIcon iconImage(java.awt.Image anIconImage) {
    setIconImage(anIconImage); return this;
  }
  public TrayIcon iconImage(java.net.URL anIconImageURL) throws IOException {
    return iconImage(ImageIO.read(anIconImageURL));
  }
  public TrayIcon iconImage(String anIconImagePathString) throws IOException, MalformedURLException {
    return iconImage(new URL(anIconImagePathString));
  }
  public TrayIcon iconImageFromStream(InputStream anIconImageInputStream) throws IOException {
    setIconImage(ImageIO.read(anIconImageInputStream)); return this;
  }

  private java.awt.Dimension iconSize;
  public java.awt.Dimension getIconSize() {
    return iconSize;
  }
  public void  setIconSize(java.awt.Dimension anIconSize) {
    iconSize = anIconSize;
    resizeTrayImage();

  }
  public TrayIcon iconSize(java.awt.Dimension anIconSize) {
    setIconSize(anIconSize); return this;
  }
  
  private java.awt.TrayIcon trayIcon;
  public java.awt.TrayIcon getTrayIcon() {
    return trayIcon;
  }
  public void setTrayIcon(java.awt.TrayIcon aTrayIcon) {
    trayIcon = aTrayIcon;
  }
  public TrayIcon trayIcon(java.awt.TrayIcon aTrayIcon) {
    setTrayIcon(aTrayIcon); return this;
  }

  private java.awt.PopupMenu popupMenu;
  public java.awt.PopupMenu getPopupMenu() {
    return popupMenu;
  }
  public void setPopupMenu(java.awt.PopupMenu aPopupMenu) {
    popupMenu = aPopupMenu;
  }
  public TrayIcon popupMenu(java.awt.PopupMenu aPopupMenu) {
    setPopupMenu(aPopupMenu); return this;
  }

  private java.awt.SystemTray systemTray;
  public java.awt.SystemTray getSystemTray() {
    return systemTray;
  }
  public void setSystemTray(java.awt.SystemTray aSystemTray) {
    systemTray = aSystemTray; 
  }
  public TrayIcon systemTray(java.awt.SystemTray aSystemTray) {
    setSystemTray(aSystemTray); return this;
  }

 /*
BufferedImage trayIconImage = ImageIO.read(getClass().getResource("/path/to/icon.png"));
int trayIconWidth = new TrayIcon(trayIconImage).getSize().width;
TrayIcon trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH));
*/

  
  private void buildTrayIcon() throws java.awt.AWTException, IOException {
    java.awt.Toolkit.getDefaultToolkit();
    setSystemTray(java.awt.SystemTray.getSystemTray());
    setTrayIcon(new java.awt.TrayIcon(getIconImage()));
    getTrayIcon().addActionListener(event -> {
      resizeTrayImage();
    });
    
    //getTrayIcon().setImageAutoSize(true);
    setPopupMenu(new java.awt.PopupMenu());
    for(java.awt.MenuItem item : getMenuItems()) {
      getPopupMenu().add(item);
    } getTrayIcon().setPopupMenu(getPopupMenu());
    getSystemTray().add(getTrayIcon());
    logger.info("Tray Icon Size:");
    
  }

  public void dumpTrayImageSize() {
    logger.info(
      new Double(getSystemTray().getTrayIconSize().getWidth()).toString()
      +"x"+
      new Double(getSystemTray().getTrayIconSize().getHeight()).toString()
    );
  }  
  public void resizeTrayImage() {
    if (getIconSize() != null) {
      getTrayIcon().setImage(getIconImage().getScaledInstance(new Double(getIconSize().getWidth()).intValue(), -1, java.awt.Image.SCALE_SMOOTH));
    } else {
      getTrayIcon().setImage(getIconImage().getScaledInstance(new Double(getTrayIcon().getSize().getWidth()).intValue(), -1, java.awt.Image.SCALE_SMOOTH));
    }
  }
  
  public TrayIcon build() {
    try {
    
      buildTrayIcon();
    } catch(java.awt.AWTException | IOException e) {
      logger.log(Level.SEVERE, "Couldn't build TrayIcon: ", e);
    } return this;
  }
  
  public TrayIcon destroy() {
    if (getSystemTray() != null && getTrayIcon() != null) {
      getSystemTray().remove(getTrayIcon());
    } return this;
  }
/*  
  public TrayIcon demo() throws Exception {
    java.awt.Font defaultFont = java.awt.Font.decode(null).deriveFont(java.awt.Font.PLAIN, 20;
    java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
    return (new TrayIcon() {
      private Integer increment = new Integer(0);
      public Integer getIncrement() {
       return increment;
      }
      public void setIncrement(Integer anIncrement) {
        increment = anIncrement;
        if (countItem != null) {
          countItem.setLabel("Count: "+String.valueOf(increment));
        }
      }
      private java.awt.MenuItem countItem = new java.awt.MenuItem("Count: 0");

      public TrayIcon init() { 
        countItem.addActionListener(event -> {
          setIncrement(increment + 1);
        }); return this;
      }
      public TrayIcon addCountItem() {
        return menuItem(countItem);
      }
      
    
    }).init()
      .iconImage("http://localhost:8080/orbit.png")
      .menuItems()
      .action("Hello World", event -> {}) //, boldFont)
//      .addCountItem()
      .action("Exit", event -> {
        this.getSystemTray().remove(this.getTrayIcon());
      }).build();
  }
  */
  

}