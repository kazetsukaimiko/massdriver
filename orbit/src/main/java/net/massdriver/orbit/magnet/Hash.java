package net.massdriver.orbit.magnet;

import java.util.Objects;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Hash {
  private static final String urnGroup = "urn";
  private static final String schemeGroup = "scheme";
  private static final String hashGroup = "hash";
  private static final String hashRegex = "(?<"+urnGroup+">urn:)?(?<"+schemeGroup+">[\\d\\w][\\d\\w]*):(?<"+hashGroup+">[0-9a-z]{5,255})";
  private String hashString;
  public String getHashString() {
    return hashString;
  }
  public void setHashString(String aHashString) {
    hashString = aHashString;
  }
  public Hash hashString(String aHashString) {
    setHashString(aHashString); return this;
  }

  private Matcher getMatcher() {
    if (getHashString() != null) {
      Matcher m = Pattern.compile(hashRegex, Pattern.CASE_INSENSITIVE).matcher(getHashString());
      if (m != null && m.matches()) {
        return m;
      }
    } return null;
  }
  
  public boolean matches() {
    return (getMatcher() != null);
  }

  public String getUrn() {
    Matcher m = getMatcher();
    if (m != null) {
      return m.group(urnGroup);
    } return null;
  }

  public String getScheme() {
    Matcher m = getMatcher();
    if (m != null) {
      return m.group(schemeGroup);
    } return null;
  }

  public String getHash() {
    Matcher m = getMatcher();
    if (m != null) {
      return m.group(hashGroup);
    } return null;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(
      getHashString()
    );
  }
}
