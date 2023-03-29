package com.cse.cseprojectroommanagementserver.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AES256 {

    private String keyDir;

    private static String alg = "AES/CBC/PKCS5Padding";

    private byte[] iv;

    private byte[] key;

    public AES256(@Value("${fileDir.aeskey}") String keyDir) throws Exception {
        this.keyDir = keyDir;
        this.key = getKey();
        this.iv = generateIv();
    }

    public String encrypt(String text) {
        System.out.println("key = " + key);
        System.out.println("iv = " + iv);
        byte[] encrypted = null;
        try{
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            encrypted = cipher.doFinal(text.getBytes("UTF-8"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String cipherText) throws Exception {
        System.out.println("key = " + key);
        System.out.println("iv = " + iv);

        byte[] decrypted = null;

        try {
            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            decrypted = cipher.doFinal(decodedBytes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(decrypted, "UTF-8");
    }

    private byte[] generateIv() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }

    private byte[] getKey() throws Exception {
        File keyFile = new File(keyDir);
        FileInputStream fis = new FileInputStream(keyFile);
        byte[] keyBytes = new byte[(int) keyFile.length()];
        fis.read(keyBytes);
        fis.close();

        return keyBytes;
    }
}
