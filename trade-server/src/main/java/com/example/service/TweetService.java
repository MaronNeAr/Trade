package com.example.service;

import com.example.dao.TweetMapper;
import com.example.pojo.Tweet;
import com.example.pojo.vo.TweetPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;

    public List<TweetPlus> getSelfTweet(String account) {
        return tweetMapper.selectTweetsByAccount(account);
    }

    public List<TweetPlus> getAllTweet() {
        return tweetMapper.selectAllTweets();
    }

    public boolean addTweet(Tweet tweet) {
        return tweetMapper.insertTweet(tweet) > 0;
    }

    public boolean followTweet(Integer id, String followers) {
        return tweetMapper.updateTweetFollowersById(id, followers) > 0;
    }
}
