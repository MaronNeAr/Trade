package com.example.service;

import com.example.dao.TweetCommentMapper;
import com.example.pojo.Tweet;
import com.example.pojo.TweetComment;
import com.example.pojo.vo.TweetPlus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetCommentService {
    @Autowired
    TweetCommentMapper tweetCommentMapper;
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
