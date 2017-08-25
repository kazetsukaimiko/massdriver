package net.massdriver.orbit.magnet;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;


import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.logging.Logger;

import java.net.URL;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.annotations.PostLoad;

@Embedded
public class MagnetLink {

  @Transient
  private final Logger logger = Logger.getLogger(getClass().getName());

  @Transient
  private static final String magnetRegex = "(magnet:\\?)(..*)";
  
  private String linkString;
  public String getLinkString() {
    return linkString;
  }
  public void setLinkString(String anLinkString) {
    linkString = anLinkString;
    postLoad();
  }

  @PostLoad  
  public void postLoad() {
    genParameters();
    genDisplayNames();
    genTrackers();
    genTopics();
    genHashes();
  }
  /*
  public MagnetLink linkString(String anLinkString) {
    setLinkString(anLinkString); return this;
  }
  */

  private Matcher getMatcher() {
    Pattern p = Pattern.compile(magnetRegex, Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(getLinkString());
    if (m.matches()) {
      return m;
    } return null;
  }
  
  public boolean matches() {
    return (getMatcher() != null);
  }

  @Transient  
  List<Parameter> parameters = new ArrayList<Parameter>();
  public List<Parameter> genParameters() {
    parameters = new ArrayList<Parameter>();
    if (getLinkString() != null) {
      Matcher m = getMatcher();
      if (m != null && m.groupCount() == 2) {
        for (String param : m.group(2).split("&")) {
          parameters.add(new Parameter().paramString(param));
        }
      }
    } return parameters;
  }
  public List<Parameter> getParameters() {
    if (parameters != null) {
      return parameters;
    } return new ArrayList<Parameter>();
  }
  
  @Transient  
  List<String> displayNames = new ArrayList<String>();
  public List<String> genDisplayNames() {
    displayNames = new ArrayList<String>();
    for (Parameter param : getParameters()) {
      if (Objects.equals((String.valueOf(param.getName())).toLowerCase(), "xt")) {
        displayNames.add(param.getValue());
      }
    } return displayNames;
  }
  public List<String> getDisplayNames() {
    if (displayNames != null) {
      return displayNames;
    } return new ArrayList<String>();
  }

  @Transient  
  List<URL> trackers = new ArrayList<URL>();
  public List<URL> genTrackers() {
    trackers = new ArrayList<URL>();
    for (Parameter param : getParameters()) {
      if (Objects.equals((String.valueOf(param.getName())).toLowerCase(), "tr")) {
        try {
          trackers.add(new URL(param.getValue()));
        } catch (Exception e) { // Bad tracker value
        }
      }
    } return trackers;
  }
  public List<URL> getTrackers() {
    if (trackers != null) {
      return trackers;
    } return new ArrayList<URL>();
  }

  @Transient  
  List<String> topics = new ArrayList<String>();
  public List<String> genTopics() {
    topics = new ArrayList<String>();
    for (Parameter param : getParameters()) {
      if (Objects.equals((String.valueOf(param.getName())).toLowerCase(), "xt")) {
        topics.add(param.getValue());
      }
    } return topics;
  }
  public List<String> getTopics() {
    if (topics != null) {
      return topics;
    } return new ArrayList<String>();
  }

  @Transient  
  List<Hash> hashes = new ArrayList<Hash>();
  public List<Hash> genHashes() {
    hashes = new ArrayList<Hash>();
    for (String topic : getTopics()) {
      Hash hash = new Hash().hashString(topic);
      if (hash.matches()) {
        hashes.add(hash);
      }
    } return hashes;
  }

  public List<Hash> getHashes() {
    if (hashes != null) {
      return hashes;
    } return new ArrayList<Hash>();
  }

  public List<MagnetLink> simpleMagnets() {
    List<MagnetLink> simpleMagnets = new ArrayList<MagnetLink>();
    for (Hash hash : getHashes()) {
      simpleMagnets.add(new MagnetLink("magnet:?xt="+hash.getHashString()));
    } return simpleMagnets;
  }
  
  public MagnetLink makeSimpleMagnet() {
    for (MagnetLink simple : simpleMagnets()) {
      return simple;
    } return null;
  }

  public String toString() {
    String build = "magnet:?";
    // Add Hashes.
    for (int i = 0; i<getHashes().size(); i++) {
      String paramName = (getHashes().size()>1)? "xt."+String.valueOf(i) : "xt";
      if (i>0) {
        build = build + "&";
      } build = build + paramName + "=" + getHashes().get(i).getHashString();
    }
    for (int i = 0; i<getTrackers().size(); i++) {
      //String paramName = (getHashes().size()>1)? "tr."+String.valueOf(i) : "tr";
      String paramName = "tr";
      build = build + "&" + paramName + "=" + getTrackers().get(i);
    } return build;
  }
  
  
  @Override
  public int hashCode() {
    return Objects.hash(
      getLinkString()
    );
  }
  
  public MagnetLink() {
  }

  public MagnetLink(String linkString) {
    setLinkString(linkString);
  }

}