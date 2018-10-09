package com.nsnc.massdriver.crypt;

import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecretKeyPool extends EncryptionPool<SecretKey> {

    public SecretKeyPool(String algorithm, int length) {
        super(algorithm, length);
    }

    @Override
    public SecretKey generate() throws NoSuchAlgorithmException {
        return CryptUtils.generateBiDirectionalKey(getAlgorithm(), getLength());
    }

    @Override
    public String uniqueIdentifier(SecretKey key) {
        return CryptUtils.hash("SHA-256", key.getEncoded());
    }

}
