package com.example.pojo.vo;

import lombok.Data;

@Data
public class TweetVO {
    private Integer id;
    private String content;
    private String picture;
    private String position;
    private String time;
    private String followers;
    private String username;
    private String icon;
}
