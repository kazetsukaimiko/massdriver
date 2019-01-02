package com.nsnc.massdriver.crypt;

import java.security.NoSuchAlgorithmException;

public class KeyPairPool extends EncryptionPool<KeyPair> {

    public KeyPairPool(String algorithm, int length) {
        super(algorithm, length);
    }

    @Override
    public KeyPair generate() throws NoSuchAlgorithmException {
        return CryptUtils.generateKeyPair(getAlgorithm(), getLength());
    }

}
