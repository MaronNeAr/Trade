package com.example.service;

import com.example.dao.TweetCommentMapper;
import com.example.dao.TweetMapper;
import com.example.pojo.Tweet;
import com.example.pojo.TweetComment;
import com.example.pojo.vo.TweetVO;
import com.example.utils.SerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class TweetService {
    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    TweetCommentMapper tweetCommentMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    MessageService messageService;

    public List<TweetVO> getSelfTweet(String account) {
        return tweetMapper.selectTweetsByAccount(account);
    }

    public List<TweetVO> getAllTweet() {
        if (redisService.get("tweetList") == null) {
            redisService.set("tweetList", tweetMapper.selectAllTweets());
        }
        return (List<TweetVO>) redisService.get("tweetList");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean publishTweet(String account, String content, String position, String image) throws Exception {
        String picUrl = "null";
        if (image != null && !image.equals("null")) {
            String uuid = UUID.randomUUID().toString();
            picUrl = "img/tweet/" + uuid + ".png";
            byte[] bytes = Base64.getDecoder().decode(image.substring(image.indexOf("base64") + 7));
            File file = new File(picUrl);
            if (file.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(bytes);
            }
        }
        messageService.publishMessage(SerializationUtil.serialize(new Tweet(null, content, picUrl, position, System.currentTimeMillis(), "", account)));
//        boolean flag = tweetMapper.insertTweet(new Tweet(null, content, picUrl, position, System.currentTimeMillis(), "", account)) > 0;
//        redisService.set("tweetList", tweetMapper.selectAllTweets());
//        return flag;
        return true;
    }

    public boolean followTweet(Integer id, String followers) {
        boolean flag = tweetMapper.updateTweetFollowersById(id, followers) > 0;
        redisService.set("tweetList", tweetMapper.selectAllTweets());
        return flag;
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
