package com.github.ccloud.common.crypto;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.ccloud.common.io.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    public static SecretKey generateKey(String key, String salt) {
        try {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch(Exception e) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void decryptFile(String in, String out, String key) {
        File inputFile = new File(in);
        File outputFile = new File(out);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] realRead = new byte[bytesRead];
                System.arraycopy(buffer, 0, realRead, 0, bytesRead);
                byte[] output = decrypt(realRead, key);
                if (output != null) {
                    outputStream.write(output);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void encryptFile(String in, String out, String key) {
        File inputFile = new File(in);
        File outputFile = new File(out);
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] realRead = new byte[bytesRead];
                System.arraycopy(buffer, 0, realRead, 0, bytesRead);
                byte[] output = encrypt(realRead, key);
                if (output != null) {
                    outputStream.write(output);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(inputStream);
            IOUtil.close(outputStream);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] encrypt(byte[] bytes, String key)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException{

        byte[] keyBytes = Base64.getDecoder().decode(key);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, new IvParameterSpec(ivBytes));
        return cipher.doFinal(bytes);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static byte[] decrypt(byte[] textBytes, String key)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {

        byte[] keyBytes = Base64.getDecoder().decode(key);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return cipher.doFinal(textBytes);
    }
}

