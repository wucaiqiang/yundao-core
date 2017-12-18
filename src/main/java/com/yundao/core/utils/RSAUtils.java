package com.yundao.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import javax.crypto.Cipher;

import com.yundao.core.log.Log;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.yundao.core.log.LogFactory;
/**
 * RSA算法加密/解密工具类
 * @author zhangmingxing
 *
 */
public abstract class RSAUtils {
	
	private static Log LOGGER = LogFactory.getLog(RSAUtils.class);

    private static final String ALGORITHOM = "RSA";
    private static final int KEY_SIZE = 1024;
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    
    private static final String FILE = "/config/rsa/";
    

    private RSAUtils() {
    }

    /**
     * 生成并返回RSA密钥对。
     */
	public static synchronized KeyPair generateKeyPair() {
        try {
        	KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss").getBytes()));
            KeyPair oneKeyPair = keyPairGen.generateKeyPair();
            return oneKeyPair;
        } catch (Exception ex) {
            LOGGER.error("KeyPairGenerator does not support a key length of " + KEY_SIZE + ".", ex);
        } 
        return null;
    }

    /**
     * * 返回生成/读取的密钥对文件的路径。
     * @throws UnsupportedEncodingException 
     */
    private static String getRSAPairFilePath(String fileName) throws UnsupportedEncodingException {
        String urlPath = RSAUtils.class.getResource(FILE).getPath();
        urlPath = URLDecoder.decode(urlPath, "UTF-8");
        return (urlPath+ fileName);
    }


    /**
     * 将指定的RSA密钥对以文件形式保存。
     */
	public  static void saveKeyPair(KeyPair keyPair,String fileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = FileUtils.openOutputStream(new File(getRSAPairFilePath(fileName)));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(keyPair);
        } catch (Exception ex) {
        	LOGGER.error("保存钥文件失败", ex);
        } finally {
        	IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * 返回RSA密钥对。
     */
    private static KeyPair getKeyPair(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = FileUtils.openInputStream(new File(getRSAPairFilePath(fileName)));
            ois = new ObjectInputStream(fis);
            KeyPair oneKeyPair = (KeyPair) ois.readObject();
            return oneKeyPair;
        } catch (Exception ex) {
        	LOGGER.error("读取秘钥文件失败", ex);
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
        return null;
    }



    /**
     * 使用指定的公钥加密数据。
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }

    /**
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }

    /**
     * 使用指定的私钥解密数据。
     */
    public static String encryptString(String fileName, String plaintext) {
        if (plaintext == null) {
            return null;
        }
        KeyPair keyPair = getKeyPair(fileName);
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt((RSAPublicKey)keyPair.getPublic(), data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }
    

    /**
     *使用给定的公钥加密给定的字符串。
     */
    public static String decryptString(String fileName, String encrypttext) {
        if (StringUtils.isBlank(encrypttext)) {
            return null;
        }
        KeyPair keyPair = getKeyPair(fileName);
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt((RSAPrivateKey)keyPair.getPrivate(), en_data);
            return new String(data);
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
        }
        return null;
    }
    
    /**
     * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
     */
    public static String decryptStringByJs(String fileName,String encrypttext) {
        String text = decryptString(fileName,encrypttext);
        if(text == null) {
            return null;
        }
        return StringUtils.reverse(text);
    }
    
    /** 返回已初始化的默认的公钥。*/ 
    public static RSAPublicKey getDefaultPublicKey(String fileName) {
        KeyPair keyPair = getKeyPair(fileName);
        if(keyPair != null) {
            return (RSAPublicKey)keyPair.getPublic();
        }
        return null;
    }
    
    /** 返回已初始化的默认的私钥。*/
    public static RSAPrivateKey getDefaultPrivateKey(String fileName) {
        KeyPair keyPair = getKeyPair(fileName);
        if(keyPair != null) {
            return (RSAPrivateKey)keyPair.getPrivate();
        }
        return null;
    }
    
    /** 返回已初始化的默认的公钥 私钥。*/
    public static KeyPair getDefaultKey(String fileName) {
        KeyPair keyPair = getKeyPair(fileName);
        if(keyPair != null) {
            return keyPair;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        byte[] content = encrypt(generateKeyPair().getPublic(),"test".getBytes());
        byte[] name = decrypt(generateKeyPair().getPrivate(),content);
        System.out.println(new String(name,"UTF-8"));
    }
}