package com.nsnc.massdriver.crypt;

import com.nsnc.massdriver.Description;
import com.nsnc.massdriver.Trait;
import com.nsnc.massdriver.chunk.Chunk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.Provider.Service;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.stream.Stream;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public final class CryptUtils {

    private static final Logger logger = Logger.getLogger(CryptUtils.class.getName());

    private static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Set<String> hashAlgorithms = fetchHashAlgorithms();
    private static final Set<String> cipherAlgorithms = fetchCipherAlgorithms();

    private static final int DEFAULT_CHUNK_SIZE = 1024 * 256;

    public static Set<String> getHashAlgorithms() {
        return hashAlgorithms;
    }

    public static Set<String> getCipherAlgorithms() {
        return cipherAlgorithms;
    }

    private static Set<String> fetchHashAlgorithms() {
        return Arrays.stream(Security.getProviders())
                .map(CryptUtils::fetchHashAlgorithms)
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<String> fetchCipherAlgorithms() {
        return Arrays.stream(Security.getProviders())
            .filter(provider -> Objects.equals(provider.getName(), "SunJCE"))
            .map(Provider::getServices)
            .flatMap(Collection::stream)
            .map(Service::getAlgorithm)
            .sorted()
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static Set<String> fetchSecurityProviders() {
        return Stream.of(Security.getProviders())
                .map(Provider::getName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<String> fetchAlgorithms(Provider provider, Predicate<Service> servicePredicate) {
        return provider
                .getServices()
                .stream()
                .filter(servicePredicate)
                .map(Service::getAlgorithm)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<String> fetchAlgorithms(Provider provider, String serviceType)  {
        return fetchAlgorithms(provider, service -> service.getType().equalsIgnoreCase(serviceType));
    }


    private static Set<String> fetchHashAlgorithms(Provider provider) {
        return fetchAlgorithms(provider, MessageDigest.class.getSimpleName());
    }

    private static Set<String> fetchKeyAlgorithms(Provider provider) {
        return fetchAlgorithms(provider, KeyGenerator.class.getSimpleName());
    }

    private static Set<String> fetchKeyPairAlgorithms(Provider provider) {
        return fetchAlgorithms(provider, KeyPairGenerator.class.getSimpleName());
    }

    /**
     * TODO: Strengthen
     * @return
     */
    private static byte[] seed() {
        ByteBuffer bb = ByteBuffer.allocate(64);
        try {
            SeekableByteChannel sbc = Files.newByteChannel(Paths.get("/dev/urandom"));
            sbc.read(bb);
        } catch (IOException e) {
            e.printStackTrace();
            bb = ByteBuffer.allocate(Long.BYTES);
            bb.putLong(System.currentTimeMillis());
        }
        return bb.array();
    }

    public static SecretKey generateBiDirectionalKey(String alg, int length) throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(alg).generateKey();
    }

    public static KeyPair generateKeyPair(String alg, int length) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(alg);
        keyPairGen.initialize(length);
        return keyPairGen.generateKeyPair();
    }

    public static Cipher getDefaultCipher() {
        return getCipher(
            "AES_256/ECB/NoPadding",
            "AES_256/CBC/NoPadding",
            "AES_256/OFB/NoPadding",
            "AES_256/CFB/NoPadding",
            "AES_256/GCM/NoPadding",
            "AES_192/ECB/NoPadding",
            "AES_192/CBC/NoPadding",
            "AES_192/OFB/NoPadding",
            "AES_192/CFB/NoPadding",
            "AES_192/GCM/NoPadding",
            "AES_128/ECB/NoPadding",
            "AES_128/CBC/NoPadding",
            "AES_128/OFB/NoPadding",
            "AES_128/CFB/NoPadding",
            "AES_128/GCM/NoPadding"
        ).get();
    }

    public static MessageDigest getDefaultDigest() {
        return getDigest(
            "SHA-512",
            "SHA-384",
            "SHA-256",
            "SHA-224",
            "SHA",
            "MD5",
            "MD2"
        ).get();
    }

    private static Optional<MessageDigest> constructDigest(String algorithm) {
        try {
            return Optional.of(MessageDigest.getInstance(algorithm));
        } catch (NoSuchAlgorithmException e) {
            return Optional.empty();
        }
    }
    public static Optional<MessageDigest> getDigest(String... algorithms) {
        return Arrays.stream(algorithms)
                .filter(hashAlgorithms::contains)
                .map(CryptUtils::constructDigest)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
    public static List<MessageDigest> getDigests() {
        return hashAlgorithms.stream()
                .map(CryptUtils::constructDigest)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private static Optional<Cipher> constructCipher(String algorithm) {
        try {
            return Optional.of(Cipher.getInstance(algorithm));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<Cipher> getCipher(String... algorithms) {
        return Arrays.stream(algorithms)
                .filter(hashAlgorithms::contains)
                .map(CryptUtils::constructCipher)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
    public static List<Cipher> getCiphers() {
        return cipherAlgorithms.stream()
                .map(CryptUtils::constructCipher)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }

    private static Map<String, MessageDigest> fetchDigests() {
        return fetchHashAlgorithms().stream()
            .map(CryptUtils::getMessageDigestFor)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(
                    MessageDigest::getAlgorithm,
                Function.identity()
            ));
    }

    private static Map<String, Cipher> fetchCiphers() {
        return fetchCipherAlgorithms().stream()
            .map(CryptUtils::getCipherFor)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(
                    Cipher::getAlgorithm,
                Function.identity()
            ));
    }

    private static Optional<MessageDigest> getMessageDigestFor(String algorithm) {
        try {
            return Optional.of(MessageDigest.getInstance(algorithm));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "Couldn't obtain message digest \""+algorithm+"\" :", e);
            return Optional.empty();
        }
    }

    private static Optional<Cipher> getCipherFor(String algorithm) {
        try {
            return Optional.of(Cipher.getInstance(algorithm));
        } catch (Exception e) {
            //logger.log(Level.WARNING, "Couldn't obtain ciper \""+algorithm+"\" :", e);
            return Optional.empty();
        }
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String hash(String algorithm, byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            ByteBuffer bb = ByteBuffer.allocate(bytes.length);
            bb.put(bytes);
            byte[] digest = messageDigest.digest(bytes);
            //messageDigest.update(bb);
            //byte[] digest = messageDigest.digest();
            String hashString = toHexString(digest);
            return hashString;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Trait", e);
        }
    }

    public static String hash(String algorithm, Path path) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);

            int buffer_size = Chunk.DEFAULT_CHUNK_SIZE;
            while (channel.position()<channel.size()) {
                long remaining = channel.size()-channel.position();
                ByteBuffer bb = ByteBuffer.allocate((buffer_size<remaining)? buffer_size : (int)remaining);
                int read = channel.read(bb);
                messageDigest.update(bb.array());
            }
            return toHexString(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash file!", e);
        }
    }

    public static List<Trait> hashAll(Path path) {
        try {
            List<MessageDigest> digestList = getDigests();
            //MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);

            int buffer_size = 1024*1024;
            while (channel.position()<channel.size()) {
                long remaining = channel.size()-channel.position();
                ByteBuffer bb = ByteBuffer.allocate((buffer_size<remaining)? buffer_size : (int)remaining);
                int read = channel.read(bb);
                for(MessageDigest messageDigest : digestList) {
                    messageDigest.update(bb);
                }
            }
            return digestList.stream().map(CryptUtils::toTrait).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash file!", e);
        }
    }


    public static List<Trait> hashAll(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        return hashAll(bb);
    }

    public static List<Trait> hashAll(ByteBuffer bb) {
        return getDigests().stream()
                .map(messageDigest -> {
                    messageDigest.update(bb);
                    return new Trait(messageDigest);
                })
                .collect(Collectors.toList());
    }



    public static Trait toTrait(MessageDigest digest) {
        return new Trait(digest);
    }

    public static Description makeDescription(byte[] bytes) {
        return new Description(CryptUtils.hashAll(bytes));
    }

    public static Description makeDescription(Path path) {
        return new Description(CryptUtils.hashAll(path));
    }
}
