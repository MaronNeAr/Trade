package com.example.pojo.vo;

import lombok.Data;

@Data
public class UserVO {
    private String username;
    private String account;
    private String phone;
    private String email;
    private String icon;

    public UserVO(String username, String account, String icon) {
        this.username = username;
        this.account = account;
        this.icon = icon;
    }

    public UserVO(String username, String account, String phone, String email, String icon) {
        this.username = username;
        this.account = account;
        this.phone = phone;
        this.email = email;
        this.icon = icon;
    }
}
