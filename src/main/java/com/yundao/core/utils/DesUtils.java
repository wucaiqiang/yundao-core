package com.yundao.core.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.yundao.core.constant.CommonConstant;

/**
 * des加密解密工具类
 * 
 * @author zhangmingxing
 *
 */
public class DesUtils {

	private final static String ALGORITHM = "DES";

	private static final String PASSWORD_CRYPT_KEY = ConfigUtils.getValue(CommonConstant.DES_KEY);

	/**
	 * 对数据进行DES解密.
	 */
	public final static String decrypt(String data) throws Exception {
		return new String(decrypt(hex2byte(data.getBytes()), PASSWORD_CRYPT_KEY.getBytes()));
	}

	/**
	 * 用指定的key对数据进行DES解密.
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	/**
	 * 对用DES加密过的数据进行加密
	 */
	public final static String encrypt(String data) throws Exception {
		return byte2hex(encrypt(data.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
	}

	/**
	 * 用指定的key对数据进行DES加密.
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(encrypt("yundao_dev"));
		System.out.println(encrypt("YunDao_Dev@com"));
		System.out.println(encrypt("yundao_qa"));
		System.out.println(encrypt("YunDao_Qa@com"));
	}
}
