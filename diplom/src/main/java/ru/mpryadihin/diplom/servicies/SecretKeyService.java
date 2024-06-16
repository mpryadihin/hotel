package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class SecretKeyService {
    @Value("${secret.key}")
    private String base64EncodedKey;

    public SecretKey getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(base64EncodedKey);
        return new SecretKeySpec(keyBytes, "AES");
    }
}
