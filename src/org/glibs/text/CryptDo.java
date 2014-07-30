package org.glibs.text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密
 */
public class CryptDo {

	/**
	 * 进行MD5加密
	 * 
	 * @return String 加密后的字符串
	 */
	public static String MD5(String source) {
		String dest = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			char[] charArray = source.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++) {
				byteArray[i] = (byte) charArray[i];
			}
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuilder hexValue = new StringBuilder();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			dest = hexValue.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return dest;
	}

	/**
	 * 进行SHA加密
	 * 
	 * @return String 加密后的字符串
	 */
	public static String Encrypte(String plainText, String algorithm) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
		}
		md.update(plainText.getBytes());
		byte[] b = md.digest();
		StringBuilder output = new StringBuilder(32);
		for (int i = 0; i < b.length; i++) {
			String temp = Integer.toHexString(b[i] & 0xff);
			if (temp.length() < 2) {
				output.append("0");
			}
			output.append(temp);
		}
		return output.toString();
	}
	// System.out.println(md.encrypte("heisetoufa", "SHA-1"));// SHA-1算法
	// System.out.println(md.encrypte("heisetoufa", "SHA-256"));// SHA-256算法
	// System.out.println(md.encrypte("heisetoufa", "SHA-512"));// SHA-512算法
	//
	// System.out.println(MD.encrypte("heisetoufa", "MD2"));//MD2算法
	// System.out.println(MD.encrypte("heisetoufa", "MD5"));//MD5算法
}