package net.massdriver.orbit.magnet;

import java.util.Objects;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Parameter {
  private static final String paramNameGroup = "paramname";
  private static final String paramNumGroup = "paramnum";
  private static final String paramValueGroup = "paramvalue";
  private static final String paramRegex = "(?<"+paramNameGroup+">[\\d\\w][\\d\\w\\.]*(?<"+paramNumGroup+">\\.\\d\\d*)?)=(?<"+paramValueGroup+">[^&][^&]*)";

  private String paramString;
  public String getParamString() {
    return paramString;
  }
  public void setParamString(String aParamString) {
    paramString = aParamString;
  }
  public Parameter paramString(String aParamString) {
    setParamString(aParamString); return this;
  }
  
  public String getRegex() {
    return paramRegex;
  }
  
  private Matcher getMatcher() {
    if (getParamString() != null) {
      Matcher m = Pattern.compile(paramRegex, Pattern.CASE_INSENSITIVE).matcher(getParamString());
      if (m != null && m.matches()) {
        return m;
      }
    } return null;
  }
  
  public String getName() {
    Matcher m = getMatcher();
    if (m != null) {
      return m.group(paramNameGroup);
    } return null;
  }

  public Integer getNumber() {
    Matcher m = getMatcher();
    if (m != null) {
      String paramNum = m.group(paramNumGroup);
      if (paramNum != null) {
        return Integer.valueOf(paramNum);
      }
    } return null;
  }

  public String getValue() {
    Matcher m = getMatcher();
    if (m != null) {
      return m.group(paramValueGroup);
    } return null;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      getParamString()
    );
  }

}

