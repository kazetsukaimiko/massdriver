package com.nsnc.massdriver.crypt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPair {
	private String algorithm;
	private String publicKey;
	private String privateKey;

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public java.security.KeyPair toJavaKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(CryptUtils.hexStringToByteArray(privateKey));
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(CryptUtils.hexStringToByteArray(publicKey));

		return new java.security.KeyPair(
				keyFactory.generatePublic(publicKeySpec),
				keyFactory.generatePrivate(privateKeySpec)
		);
	}

	public KeyPair() {
	}

	public KeyPair(String algorithm, java.security.KeyPair javaKeyPair) {
		this(
				algorithm,
				CryptUtils.toHexString(javaKeyPair.getPrivate().getEncoded()),
				CryptUtils.toHexString(javaKeyPair.getPublic().getEncoded())
		);
	}

	public KeyPair(String algorithm, String privateKey, String publicKey) {
		this.algorithm = algorithm;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	@Override
	public String toString() {
		return String.join("-",
				algorithm, publicKey, privateKey
		);
	}
}
