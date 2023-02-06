package com.example.pojo;

import lombok.Data;

@Data
public class User {
    Integer id;
    String username;
    String password;
    String account;
    String icon;
    Boolean status;
    Boolean admin;
}
