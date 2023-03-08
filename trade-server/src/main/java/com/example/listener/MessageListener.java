package com.example.listener;

import com.example.dao.TweetMapper;
import com.example.pojo.Tweet;
import com.example.service.RedisService;
import com.example.utils.SerializationUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageListener {
    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    RedisService redisService;

    @RabbitListener(queues = "message-queue")
    public void receive(String message) {
        System.out.println("已完成" + message + "的发送");
    }

    @RabbitListener(queues = "message-queue")
    public void publish(byte[] bytes) throws IOException, ClassNotFoundException {
        Tweet tweet = SerializationUtil.deserialize(bytes, Tweet.class);
        tweetMapper.insertTweet(tweet);
        redisService.set("tweetList", tweetMapper.selectAllTweets());
    }
}
