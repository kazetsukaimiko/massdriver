package com.nsnc.massdriver.crypt;

public abstract class EncryptionPool<K> extends Pool<K> {

    private String algorithm;
    private int length;


    public EncryptionPool(String algorithm, int length) {
        this.algorithm = algorithm;
        this.length = length;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
