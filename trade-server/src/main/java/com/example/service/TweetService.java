package com.example.service;

import com.example.dao.TweetCommentMapper;
import com.example.dao.TweetMapper;
import com.example.pojo.Tweet;
import com.example.pojo.TweetComment;
import com.example.pojo.vo.TweetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    TweetCommentMapper tweetCommentMapper;

    public List<TweetVO> getSelfTweet(String account) {
        return tweetMapper.selectTweetsByAccount(account);
    }

    public List<TweetVO> getAllTweet() {
        return tweetMapper.selectAllTweets();
    }

    public boolean addTweet(Tweet tweet) {
        return tweetMapper.insertTweet(tweet) > 0;
    }

    public boolean followTweet(Integer id, String followers) {
        return tweetMapper.updateTweetFollowersById(id, followers) > 0;
    }

    public List<TweetComment> getAllTweetComment(Integer tid) {
        return tweetCommentMapper.selectTweetCommentByTid(tid);
    }
    public boolean addTweetComment(TweetComment tweetComment) {
        return tweetCommentMapper.insertTweetCommentByTid(tweetComment) > 0;
    }
    public boolean deleteTweetComment(Integer tid) {
        return tweetCommentMapper.deleteTweetCommentByTid(tid) > 0;
    }
}
