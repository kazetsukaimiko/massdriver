package net.massdriver.orbit.util;

import java.util.List;
import java.util.Arrays;
import java.util.logging.Logger;

public class HaloName {

  private static final Logger logger = Logger.getLogger(HaloName.class.getName());

  private static final List<String> names = Arrays.asList(new String[] {
    "Donut",
    "Penguin",
    "Stumpy",
    "Whicker",
    "Shadow",
    "Howard",
    "Wilshire",
    "Darling",
    "Disco",
    "Jack",
    "The Bear",
    "Sneak",
    "The Big L",
    "Whisp",
    "Wheezy",
    "Crazy",
    "Goat",
    "Pirate",
    "Saucy",
    "Hambone",
    "Butcher",
    "Walla Walla",
    "Snake",
    "Caboose",
    "Sleepy",
    "Killer",
    "Stompy",
    "Mopey",
    "Dopey",
    "Weasel",
    "Ghost",
    "Dasher",
    "Grumpy",
    "Hollywood",
    "Tooth",
    "Noodle",
    "King",
    "Cupid",
    "Prancer"
  });

  public static List<String> getNames() {
    return names;
  }
  
}