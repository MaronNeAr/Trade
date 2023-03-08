package com.example.service;

import com.example.dao.UserMapper;
import com.example.pojo.User;
import com.example.pojo.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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

    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUserInfo(UserVO userVO) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String picUrl = "img/icon/" + uuid + ".png";
        byte[] bytes = Base64.getDecoder().decode(userVO.getIcon().substring(userVO.getIcon().indexOf("base64") + 7));
        File file = new File(picUrl);
        if (file.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        }
        return userMapper.updateUser(new UserVO(userVO.getUsername(), userVO.getAccount(), picUrl)) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public  Boolean updateUserPhone(String account, String phone) {
        return userMapper.updatePhone(account, phone) > 0;
    }

    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }
}
