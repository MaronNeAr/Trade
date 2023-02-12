package com.example.pojo.vo;

import lombok.Data;

@Data
public class TweetVO {
    Integer id;
    String content;
    String picture;
    String position;
    String time;
    String followers;
    String username;
    String icon;
}
