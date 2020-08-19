package com.java.until.ras;


import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.codec.binary.Base64;

public class RSAUtil {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
    }
    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param
     *
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str) throws Exception{
//        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnLHbVWqXgzpMSVw/EMcHlfkfV" +
//                "bdZgzLOOL0uMw2t3K/w1RL73nBwI3Oo3aYMEOpPh+Q8eeCZRzpOOt92CyPSMvtfq" +
//                "Kctdxl3kNr3VfeQe1ECsT4c3H9Pdh9LgOESnpU2GmAoITSx+8X5TwZRTNZXuyqyx" +
//                "xHOMZ4KVFsfVLPBE1QIDAQAB";
        String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCM6x0Lukj2FBhZ+FDMne1+xfU7kIihiLHdEtXwYiKE+WevhD+LCkBMfJuFqNNt3G2cTCK6QEAt0ZR39PzJPq7yOdHaNChbqOF6WIzviZH8CQ3LuTAs0s50S6OeDnfX1PT2Q3PWf3+pq4xXX6FvB2f3D2m5Lt2p18UwHKB9Q6n5zQIDAQAB";
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param
     *
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str) throws Exception{
        String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzrHQu6SPYUGFn4UMyd7X7F9TuQiKGIsd0S1fBiIoT5Z6+EP4sKQEx8m4Wo023cbZxMIrpAQC3RlHf0/Mk+rvI50do0KFuo4XpYjO+JkfwJDcu5MCzSznRLo54Od9fU9PZDc9Z/f6mrjFdfoW8HZ/cPabku3anXxTAcoH1DqfnNAgMBAAECgYABSSvsrXh49ln6iC+EjnRaUcgZEQqOHscwszlMhji2aeguS9CgnilUCVcJ8y5qtDYz1OYEOQ3c3SqzpYKg+3dxLtOP+uZXK8UUnRPmNKbO1moeX0YMdGQWaZ9P8lpvG8AK/rzxSbCuu9O4e1gkbxkb+r7v3sB+9AIc7M7c4yMCqQJBAO5YDu0mLsPWvwC/v3OIq5/1KBUNo1rx2qV3D9CR8CKyyY1LmO1am0f4SvRj3TfopjXKOHpg1KQd9ZlO5jnlrbsCQQCXW3t0P8piyzW1wzhpgkFU/p3ocyKJnArKQG60vsA7nJyJqYOB483074dBBSf37wPa8W7bty4HiAGcPSwD0ToXAkEAj9am02nKqTetN4eAw/nEEZnDIcmEPztcR6Vt7uJT+KzLRDFOTlfzBGEM4yZtd/M4c5fhs8FCMvU9pdLe0CwTRwJBAIYm0GI11AUChcVnlY12pL9Xuc/4lGkRhELFHxpWY32yjtA5DdVHexmYX/ISRt2q3GjnStG1JzgHpR283lE31dsCQFRugxnFZGykpEdanIqeJjOYdOS7aGBGlOrFrLtSwNqTl4DDOkeW12f8We9/RIsjoUeGywM35KHpl718FKQwT1c=";
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputByte.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(inputByte,i,i+128));
            sb.append(new String(doFinal));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
		String flag = RSAUtil.decrypt("hKujOXgpOG5LJ+KOcei9hhJ49/rRoWAXEvHf+IcqGvqaOvTEjcEKDbIKZKijrNnfZ1SvAPJDkVg7pZrkJqRCrY/L7wZvQunO6Zh31A8wXUQdJhLFQQGsgRijaXfrKUigtcRB9fgDb+Z9ErVLS6KxqzkTqjhiMn+GmQuT6BlfAEM=");
		System.out.println(flag);
	}


}  