package com.example.service;

import com.example.dao.UserMapper;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserByAccount(String account) {
        return userMapper.selectUserByAccount(account);
    }

    public Boolean registerUser(String account, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String key = "sufe-computer-20";
        String iv = new String(account);
        if (iv.length() > 16) iv = iv.substring(0, 16);
        else while(iv.length() < 16) iv = iv + "-";

        byte[] pwdBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameter = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameter);

        byte[] encryptedBytes = cipher.doFinal(pwdBytes);

        String ciphertext = Base64.getEncoder().encodeToString(encryptedBytes);
        return userMapper.insertUser(account, ciphertext) > 0;
    }

    public int loginUser(String account, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        User user = userMapper.selectUserByAccount(account);
        if (user != null) {
            String iv = new String(account);
            if (iv.length() > 16) iv = iv.substring(0, 16);
            else while(iv.length() < 16) iv = iv + "-";
            String key = "sufe-computer-20";
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameter = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameter);

            byte[] pwdBytes = cipher.doFinal(Base64.getDecoder().decode(user.getPassword()));
            String pwd = new String(pwdBytes, StandardCharsets.UTF_8);
            return pwd.equals(password) ? 1 : 0;
        }
        return -1;
    }

    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }
}
