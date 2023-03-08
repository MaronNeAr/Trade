package com.example.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tweet implements Serializable {
    private Integer id;
    private String content;
    private String picture;
    private String position;
    private Long time;
    private String followers;
    private String author;

    public Tweet(Integer id, String content, String picture, String position, Long time, String followers, String author) {
        this.id = id;
        this.content = content;
        this.picture = picture;
        this.position = position;
        this.time = time;
        this.followers = followers;
        this.author = author;
    }
}
