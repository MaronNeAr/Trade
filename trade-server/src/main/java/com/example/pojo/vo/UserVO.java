package com.example.pojo.vo;

import lombok.Data;

@Data
public class UserVO {
    String username;
    String account;
    String icon;

    public UserVO(String username, String account, String icon) {
        this.username = username;
        this.account = account;
        this.icon = icon;
    }
}
