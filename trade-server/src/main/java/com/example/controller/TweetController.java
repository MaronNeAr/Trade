package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.constant.Constants;
import com.example.pojo.Tweet;
import com.example.pojo.vo.TweetPlus;
import com.example.service.TweetService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TweetController {
    @Autowired
    TweetService tweetService;

    @Autowired
    UserService userService;

    @Configuration
    public static class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/**")
                    .addResourceLocations(Constants.IMAGES_PATH);
        }
    }

    @GetMapping("/tweets")
    public Object getTweetList() {
        try {
            return new SuccessMessage<List<TweetPlus>>("获取推文信息成功", tweetService.getAllTweet()).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取推文信息失败").getMessage();
        }
    }

    @PostMapping("/publish")
    public Object publishTweet(HttpServletRequest req) {
        try {
            Tweet tweet = new Tweet(
                    null,
                    req.getParameter("content"),
                    req.getParameter("picture"),
                    req.getParameter("position"),
                    req.getParameter("time"),
                    req.getParameter("followers"),
                    req.getParameter("author")
            );
            return new SuccessMessage<Boolean>("发表成功", tweetService.addTweet(tweet)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("发表推文失败").getMessage();
        }
    }

    @PostMapping("tweet/follow")
    public Object postFollow(HttpServletRequest req) {
        try {
            Integer id = Integer.valueOf(req.getParameter("tid"));
            String followers = req.getParameter("followers");
            return new SuccessMessage<Boolean>("点赞成功", tweetService.followTweet(id, followers)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("点赞失败，服务器内部错误").getMessage();
        }
    }
}
