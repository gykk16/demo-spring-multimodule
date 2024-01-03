package io.glory.mcore.util.cipher;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

    public static final String AES_ECB_PKCS_5_PADDING = "AES/ECB/PKCS5Padding";
    public static final String AES_CBC_PKCS_5_PADDING = "AES/CBC/PKCS5Padding";

    private static final String  AES             = "AES";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private AESCipher() {
    }

    public static String encrypt(String input, String key, String iv) throws GeneralSecurityException {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(DEFAULT_CHARSET));

        return encrypt(input, secretKey, ivParameterSpec);
    }

    public static String decrypt(String cipherText, String key, String iv) throws GeneralSecurityException {

        SecretKey secretKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(DEFAULT_CHARSET));

        return decrypt(cipherText, secretKey, ivParameterSpec);
    }

    public static String encrypt(String input, SecretKey key, IvParameterSpec iv) throws GeneralSecurityException {
        return encrypt(input, key, iv, AES_CBC_PKCS_5_PADDING);
    }

    public static String encrypt(String input, SecretKey key, IvParameterSpec iv, String mode)
            throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance(mode);

        if (mode.equals(AES_ECB_PKCS_5_PADDING)) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        }

        byte[] cipherText = cipher.doFinal(input.getBytes(DEFAULT_CHARSET));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv) throws GeneralSecurityException {
        return decrypt(cipherText, key, iv, AES_CBC_PKCS_5_PADDING);
    }

    public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv, String mode)
            throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance(mode);

        if (mode.equals(AES_ECB_PKCS_5_PADDING)) {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
        }

        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText, DEFAULT_CHARSET);
    }

    public static SecretKeySpec getSecretKey(String key, String digestAlgorithm) throws NoSuchAlgorithmException {
        return new SecretKeySpec(getMessageDigest(digestAlgorithm).digest(key.getBytes(UTF_8)), AES);
    }

    public static IvParameterSpec getInitialVector(String iv, String digestAlgorithm) throws NoSuchAlgorithmException {
        return new IvParameterSpec(getMessageDigest(digestAlgorithm).digest(iv.getBytes(UTF_8)));
    }

    private static String customDecoder(String value) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(value, UTF_8);
        return URLDecoder.decode(encode, UTF_8);
    }

    private static MessageDigest getMessageDigest(String digestAlgorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(digestAlgorithm);
    }

}
