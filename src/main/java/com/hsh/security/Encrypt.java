package main.java.com.hsh.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Encrypt {

    /**
     * 무작위 문자열 Salt 생성
     */
    public String getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];

        sr.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * SHA-256 알고리즘 적용
     */
    public String getEncrypt(String pwd, String salt) {
        String result= "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((pwd + salt).getBytes());
            byte[] pwdSalt = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : pwdSalt) {
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
