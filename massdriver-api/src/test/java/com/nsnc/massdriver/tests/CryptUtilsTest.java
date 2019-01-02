package com.nsnc.massdriver.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.crypt.CryptUtils;
import com.nsnc.massdriver.crypt.KeyPairPool;
import com.nsnc.massdriver.crypt.SecretKeyPool;

public class CryptUtilsTest extends FileSystemTest {



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
        int testSize = 100;
        KeyPairPool kpp = new KeyPairPool("RSA", 1024);
        kpp.populate(testSize);
        assertEquals(testSize, kpp.getKeys().size());

    }

    @Test
    public void testSecretKeyPool() throws NoSuchAlgorithmException {
        int testSize = 100;
        SecretKeyPool skp = new SecretKeyPool("AES", 256);
        skp.populate(testSize);
        assertEquals(testSize, skp.getKeys().size());
    }


    @Test
    public void hashAllChunksTest() {
        byte[] byteArray = new byte[1024*1024];
        RANDOM.nextBytes(byteArray);

        ByteBuffer byteBuffer = ByteBuffer.allocate(byteArray.length);
        byteBuffer.put(byteArray);

        byteBuffer.position(byteBuffer.capacity());

        // Bad
        List<Trait> defaultTraits = CryptUtils.hashAll(new byte[] {});

        List<Trait> randomTraits = CryptUtils.hashAll(byteArray);
        List<Trait> randomTraitsBB = CryptUtils.hashAll(byteBuffer);

        testBadTraits(defaultTraits, randomTraits);
        testBadTraits(defaultTraits, randomTraitsBB);

        testGoodTraits(randomTraits, randomTraitsBB);
    }

    @Test
    public void hashAllPathTest() {
        // Bad
        List<Trait> defaultTraits = CryptUtils.hashAll(new byte[] {});

        List<Trait> pathHash = CryptUtils.hashAll(randomFile);

        testBadTraits(defaultTraits, pathHash);
    }

    private void testBadTraits(List<Trait> badTraits, List<Trait> testTraits) {
        // Find baddies
        testTraits.forEach(trait -> assertFalse(badTraits.stream().anyMatch(trait::equals)));

        // Trait.matches
        assertFalse(Trait.matches(badTraits, testTraits));
        assertFalse(Trait.matches(testTraits, badTraits));
    }

    private void testGoodTraits(List<Trait> oneEquals, List<Trait> theOther) {
        // These should equal
        oneEquals.forEach(trait -> assertTrue(theOther.stream().anyMatch(trait::equals)));
        theOther.forEach(trait -> assertTrue(oneEquals.stream().anyMatch(trait::equals)));

        assertTrue(Trait.matches(oneEquals, theOther));
        assertTrue(Trait.matches(theOther, oneEquals));

    }
}
