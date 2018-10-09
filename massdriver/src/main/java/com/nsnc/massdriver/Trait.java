package com.nsnc.massdriver;

import com.nsnc.massdriver.crypt.CryptUtils;

import java.security.MessageDigest;
import java.util.Objects;

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
}
