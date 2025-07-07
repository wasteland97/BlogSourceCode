package com.wasteland.blogsourcecode.mybatisdemo.plugin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author wasteland
 * @create 2025-04-08
 */
public class DigestUtils {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    /**
     * 计算字符串的MD5值
     * @param input 输入字符串
     * @return 32位小写MD5值
     */
    public static String md5(String input) {
        return digest(input, "MD5");
    }

    /**
     * 计算字符串的SHA-1值
     * @param input 输入字符串
     * @return 40位小写SHA-1值
     */
    public static String sha1(String input) {
        return digest(input, "SHA-1");
    }

    /**
     * 计算字符串的SHA-256值
     * @param input 输入字符串
     * @return 64位小写SHA-256值
     */
    public static String sha256(String input) {
        return digest(input, "SHA-256");
    }

    /**
     * 计算字符串的SHA-512值
     * @param input 输入字符串
     * @return 128位小写SHA-512值
     */
    public static String sha512(String input) {
        return digest(input, "SHA-512");
    }

    /**
     * 通用摘要计算方法
     * @param input 输入字符串
     * @param algorithm 算法名称
     * @return 摘要字符串
     */
    private static String digest(String input, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字节数组转十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_CHARS[v >>> 4];
            hexChars[i * 2 + 1] = HEX_CHARS[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Base64编码
     * @param input 输入字符串
     * @return Base64编码结果
     */
    public static String base64Encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解码
     * @param input Base64编码字符串
     * @return 解码后的原始字符串
     */
    public static String base64Decode(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 计算字符串的HMAC-SHA256签名
     * @param data 要签名的数据
     * @param key 密钥
     * @return HMAC-SHA256签名
     */
    public static String hmacSha256(String data, String key) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA256");
            mac.init(new javax.crypto.spec.SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] result = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
