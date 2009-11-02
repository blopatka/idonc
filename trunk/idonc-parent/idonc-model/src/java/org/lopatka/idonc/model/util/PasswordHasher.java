package org.lopatka.idonc.model.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public class PasswordHasher {

	public static byte[] createSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bSalt = new byte[8];
		random.nextBytes(bSalt);
		return bSalt;
	}

	public static byte[] getHash(int iterationNumber, String password,
			byte[] salt) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < iterationNumber; i++) {
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}
	
	public static byte[] getHash(int iterationNumber, String password, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] saltB = base64ToByte(salt);
		byte[] hash = getHash(iterationNumber, password, saltB);
		return hash;
	}

	/**
	 * From a base 64 representation, returns the corresponding byte[]
	 */
	public static byte[] base64ToByte(String data) {
		byte[] encoded = Base64.decodeBase64(data.getBytes());
		return encoded;
	}
	
	/**
	 * From byte[] returns base 64 representation
	 * @throws UnsupportedEncodingException 
	 */
	public static String byteToBase64(byte[] data) throws UnsupportedEncodingException {
		byte[] decoded = Base64.encodeBase64(data);
		return new String(decoded, "UTF-8");
	}
}
