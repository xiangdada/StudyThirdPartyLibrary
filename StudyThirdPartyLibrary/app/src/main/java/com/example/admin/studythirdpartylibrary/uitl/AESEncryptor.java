package com.example.admin.studythirdpartylibrary.uitl;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xpf on 2017/7/12.
 */

public class AESEncryptor {

    public final static String CHARSET = "utf-8";

    public static String DEFAULT_KEY = "59d9e5e56532b5d71b5ac5e314134064";

    public static byte[] iv = new byte[] {0x00, 0x01, 0x02, 0x03, 0x04, 0x05,
            0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};

    /**
     * AES加密
     * @param msg 待加密文字
     * @return
     * @throws Exception
     */
    public static String encrypt(String msg) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Key key = new SecretKeySpec(DEFAULT_KEY.getBytes(CHARSET), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        IvParameterSpec ips = new IvParameterSpec(iv);
        c.init(Cipher.ENCRYPT_MODE, key, ips);
        return Base64.encode(c.doFinal(msg.getBytes(CHARSET)));
    }
    /**
     * AES解密
     * @param ciphertext 解密后文字
     * @return
     * @throws Exception
     */
    public static String decrypt(String ciphertext) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        Key key = new SecretKeySpec(DEFAULT_KEY.getBytes(CHARSET), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        IvParameterSpec ips = new IvParameterSpec(iv);
        c.init(Cipher.DECRYPT_MODE, key, ips);
        return new String(c.doFinal(Base64.decode(ciphertext)), CHARSET);

    }
}
