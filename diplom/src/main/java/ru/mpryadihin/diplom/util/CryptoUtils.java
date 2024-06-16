package ru.mpryadihin.diplom.util;

import org.springframework.stereotype.Component;
import ru.mpryadihin.diplom.servicies.SecretKeyService;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class CryptoUtils {



    public String encrypt(String plainText, SecretKey secretKey)  {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // Генерация IV (Initialization Vector)
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        byte[] encryptedBytes = new byte[0];
        try {
            encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public  String decrypt(String encryptedText, SecretKey secretKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

        // Генерация IV (Initialization Vector)
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = new byte[0];
        try {
            decryptedBytes = cipher.doFinal(decodedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
