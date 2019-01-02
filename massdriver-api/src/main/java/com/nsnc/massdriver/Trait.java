package com.nsnc.massdriver;

import com.nsnc.massdriver.crypt.CryptUtils;

import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by luna on 8/2/17.
 * A bean containing a single trait.
 */
public class Trait {
    private String name;
    private String content;

    /**
     * Gets the hash name, typically the algorithm used.
     * @return The hash name.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the hash content.
     * @return The hash content.
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Trait() {

    }

    public Trait(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public static String safeAlgorithmName(String algorithm) {
        return algorithm.replaceAll("\\/", "-");
    }

    public Trait(MessageDigest messageDigest) {
        this.name = safeAlgorithmName(messageDigest.getAlgorithm());
        this.content = CryptUtils.toHexString(messageDigest.digest());
    }

    @Override
    public String toString() {
        return name + ":" + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trait trait = (Trait) o;
        return Objects.equals(name, trait.name) &&
                Objects.equals(content, trait.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * @param trait
     * @return True if same Trait with different values, otherwise false.
     */
    public boolean fails(Trait trait) {
        return Optional.ofNullable(trait)
                .map(Trait::getName)
                .filter(name -> Objects.equals(getName(), name))
                .map(name -> !Objects.equals(getContent(), trait.getContent()))
                .orElse(false);
    }

    public static boolean matches(List<Trait> search, List<Trait> subject) {
        return
                search.stream()
                        .noneMatch(se -> subject.stream().anyMatch(se::fails))
                        &&
                search.stream()
                        .anyMatch(se -> subject.stream().anyMatch(se::equals))
                        &&
                subject.stream()
                        .noneMatch(su -> search.stream().anyMatch(su::fails))
                        &&
                subject.stream()
                        .anyMatch(su -> search.stream().anyMatch(su::equals));
    }
}
