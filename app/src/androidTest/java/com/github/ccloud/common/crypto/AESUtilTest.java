package com.github.ccloud.common.crypto;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.ccloud.util.ContextHolder;

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

public class AESUtilTest {

    private String fileName = UUID.randomUUID().toString();

    private String fileNameEncrypted = fileName + "-encrypted";

    private String rawFilePath;

    private String encryptedFilePath;

    private static String encryptKey;

    private String tempFilePath;

    @BeforeClass
    public static void beforeClass() {
        encryptKey = Base64.getEncoder().encodeToString(AESUtil.generateKey("12345678", "12345678").getEncoded());
    }

    @Before
    public void before() throws IOException, NoSuchAlgorithmException {
        String path = ContextHolder.getContext().getFilesDir().getPath();
        rawFilePath = path + '/' + fileName;
        Files.createFile(Paths.get(rawFilePath));
        Files.write(Paths.get(rawFilePath), rawFilePath.getBytes(StandardCharsets.UTF_8));

        encryptedFilePath = path + '/' + fileNameEncrypted;
        Files.createFile(Paths.get(encryptedFilePath));

        tempFilePath =  path + '/' + UUID.randomUUID().toString();
        Files.createFile(Paths.get(tempFilePath));
    }

    @After
    public void after() throws IOException {
        Files.delete(Paths.get(rawFilePath));
        Files.delete(Paths.get(encryptedFilePath));
        Files.delete(Paths.get(tempFilePath));
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_encryptFile_when_all_is_normal_then_return_success() throws IOException {
        byte[] rawBytes = Files.readAllBytes(Paths.get(rawFilePath));
        AESUtil.encryptFile(rawFilePath, encryptedFilePath, encryptKey);
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(encryptedFilePath));

        assertThat(rawBytes, IsNot.not(equalTo(encryptedBytes)));
    }

    @Test
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void test_decryptFile_when_all_is_normal_then_return_success() throws IOException {
        byte[] rawBytes = Files.readAllBytes(Paths.get(rawFilePath));
        AESUtil.encryptFile(rawFilePath, encryptedFilePath, encryptKey);
        AESUtil.decryptFile(encryptedFilePath, tempFilePath, encryptKey);
        byte[] decryptBytes = Files.readAllBytes(Paths.get(tempFilePath));
        assertArrayEquals(rawBytes, decryptBytes);
    }
}

