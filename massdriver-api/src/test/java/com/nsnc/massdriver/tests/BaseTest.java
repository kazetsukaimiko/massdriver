package com.nsnc.massdriver.tests;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseTest {
    protected static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

    protected static final Random RANDOM = new Random(System.currentTimeMillis());
    public static final List<String> NAMES = Stream.of(
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
    ).collect(Collectors.toList());

    public static String randomName() {
        return NAMES.get(RANDOM.nextInt(NAMES.size()));
    }
}
