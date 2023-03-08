package com.example.pojo;

import lombok.Data;

@Data
public class TweetComment {
    private Integer id;
    private String observer;
    private String comment;
    private Integer tweetId;
    public TweetComment(Integer id, String observer, String comment, Integer tweetId) {
        this.id = id;
        this.observer = observer;
        this.comment = comment;
        this.tweetId = tweetId;
    }
}
