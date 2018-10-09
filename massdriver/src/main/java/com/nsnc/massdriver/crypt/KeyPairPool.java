package com.nsnc.massdriver.crypt;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeyPairPool extends EncryptionPool<KeyPair> {

    public KeyPairPool(String algorithm, int length) {
        super(algorithm, length);
    }

    @Override
    public KeyPair generate() throws NoSuchAlgorithmException {
        return CryptUtils.generateKeyPair(getAlgorithm(), getLength());
    }

    @Override
    public String uniqueIdentifier(KeyPair key) {
        return Stream.of(
                key.getPrivate().getEncoded(),
                key.getPublic().getEncoded()
        )
                .map(k -> CryptUtils.hash(getAlgorithm(), k))
                .collect(Collectors.joining("/"));
    }


}
