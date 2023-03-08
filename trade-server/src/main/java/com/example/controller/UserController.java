package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.pojo.User;
import com.example.pojo.vo.UserVO;
import com.example.service.RedisService;
import com.example.service.SmsService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    SmsService smsService;

    @Autowired
    RedisService redisService;

    @GetMapping("/users")
    public Object getUsers() {
        return new SuccessMessage<List<User>>(null, userService.getAllUsers()).getMessage();
    }

    @PostMapping("/register")
    public Object register(HttpServletRequest req) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        try {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            if (account == null || password == null) return new ErrorMessage("请输入完整的账户和密码").getMessage();
            for(User user : userService.getAllUsers()) {
                if (account.equals(user.getAccount())) {
                    return new ErrorMessage("该账号已注册").getMessage();
                }
            }
            return new SuccessMessage<Boolean>("注册成功", userService.registerUser(account, password)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("注册失败").getMessage();
        }

    }

    @PostMapping("/login")
    public Object login(HttpServletRequest req) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        try {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            if (account == null || password == null) return new ErrorMessage("请输入完整的账户和密码").getMessage();
            int flag = userService.loginUser(account, password);
            if (flag == -1) return new ErrorMessage("您尚未注册").getMessage();
            else if (flag == 0) return new ErrorMessage("您的账号或者密码有误").getMessage();
            else {
                User user = userService.getUserByAccount(account);
                return new SuccessMessage<UserVO>("登录成功", new UserVO(user.getUsername(), user.getAccount(), user.getPhone(), user.getEmail(), user.getIcon())).getMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("登录失败").getMessage();
        }

    }

    @PostMapping("/update")
    public Object updateUserInfo(HttpServletRequest req) {
        try {
            String icon = req.getParameter("icon");
            String username = req.getParameter("username");
            String account = req.getParameter("account");
            if (userService.updateUserInfo(new UserVO(username, account, icon))) {
                User user = userService.getUserByAccount(account);
                return new SuccessMessage<UserVO>("更新用户信息成功", new UserVO(user.getUsername(), user.getAccount(), user.getPhone(), user.getEmail(), user.getIcon())).getMessage();
            } else return new ErrorMessage("更新用户信息失败").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("更新用户信息失败").getMessage();
        }
    }

    @PostMapping("/send/auth-code")
    public Object sendAuthCode(HttpServletRequest req) {
        try {
            String phone = req.getParameter("phone");
            String authCode = SmsService.generateCode();
            if (phone == null) return new ErrorMessage("请输入正确的电话号码，例如+86139xxxxxxxx");
//            smsService.sendSmsMessage(phone, authCode);
            System.out.println(authCode);
            redisService.set(phone, authCode, 300);
            return new SuccessMessage("发送验证码成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("发送验证码失败").getMessage();
        }
    }

    @PostMapping("/update/phone")
    public Object updatePhone(HttpServletRequest req) {
        try {
            String phone = req.getParameter("phone");
            String account = req.getParameter("account");
            String inputCode = req.getParameter("code");
            if (phone == null || account == null || inputCode == null) return new ErrorMessage("参数缺失，请重试").getMessage();
            if (!redisService.hasKey(phone)) return new ErrorMessage("验证码已过期，请重新获取").getMessage();
            if (!redisService.get(phone).equals(inputCode)) return new ErrorMessage("验证码错误，请重新输入").getMessage();
            userService.updateUserPhone(account, phone);
            return new SuccessMessage("更新用户电话成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("更新用户电话失败").getMessage();
        }
    }
}
