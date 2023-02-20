package com.example.pojo;

import lombok.Data;

@Data
public class Tweet {
    Integer id;
    String content;
    String picture;
    String position;
    Long time;
    String followers;
    String author;

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
