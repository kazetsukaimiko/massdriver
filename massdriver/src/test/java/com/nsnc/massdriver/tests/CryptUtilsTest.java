package com.nsnc.massdriver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.crypt.KeyPairPool;
import com.nsnc.massdriver.crypt.SecretKeyPool;

public class CryptUtilsTest extends MemoryTest {

    @Test
    public void testGenerateKeyUniqueness() {
        List<SecretKey> secretKeys = IntStream.range(0, 10)
                .mapToObj(i -> {
                    try {
                        return CryptUtils.generateBiDirectionalKey("AES", 256);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        fail("Shouldn't have problems generating");
                        return null;
                    }
                })
                .collect(Collectors.toList());
        Set<String> uniqueKeys = secretKeys.stream()
                .map(SecretKey::getEncoded)
                .map(b -> CryptUtils.hash("SHA-256", b))
                .collect(Collectors.toSet());

        assertEquals(secretKeys.size(), uniqueKeys.size());
    }

    @Test
    public void testProviders() {
        CryptUtils.fetchSecurityProviders()
                .forEach(System.out::println);
    }

    @Test
    public void testAlgorithms() {
        CryptUtils.getHashAlgorithms()
                .forEach(System.out::println);
    }

    @Test
    public void testKeyPairPool() throws NoSuchAlgorithmException {
        int testSize = 10000;
        KeyPairPool kpp = new KeyPairPool("RSA", 1024);
        kpp.populate(testSize);
        assertEquals(kpp.getKeys().size(), testSize);

    }

    @Test
    public void testSecretKeyPool() throws NoSuchAlgorithmException {
        int testSize = 10000;
        SecretKeyPool skp = new SecretKeyPool("AES", 256);
        skp.populate(testSize);
        assertEquals(skp.getKeys().size(), testSize);
    }
}
