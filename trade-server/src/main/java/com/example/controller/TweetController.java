package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.constant.Constants;
import com.example.pojo.Tweet;
import com.example.pojo.TweetComment;
import com.example.pojo.vo.TweetVO;
import com.example.service.TweetService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TweetController {
    @Autowired
    TweetService tweetService;

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
            return new SuccessMessage<List<TweetVO>>("获取推文信息成功", tweetService.getAllTweet()).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取推文信息失败").getMessage();
        }
    }

    @PostMapping("/publish")
    public Object publishTweet(HttpServletRequest req) {
        try {
            String account = req.getParameter("account");
            String content = req.getParameter("content");
            String position = req.getParameter("position");
            String image = req.getParameter("image");
            if (account == null || content == null || position == null) return new ErrorMessage("参数缺失").getMessage();
            return new SuccessMessage<Boolean>("发表成功", tweetService.publishTweet(account, content, position, image)).getMessage();
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

    @GetMapping("/comment/{tid}")
    public Object getTweetCommentList(@PathVariable("tid") Integer tid) {
        try {
            return new SuccessMessage<List<TweetComment>>("获取推文评论成功", tweetService.getAllTweetComment(tid)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取推文信评论失败").getMessage();
        }
    }

    @PostMapping("/comment")
    public Object addTweetComment(HttpServletRequest req) {
        try {
            TweetComment tweetComment = new TweetComment(
                    null,
                    req.getParameter("observer"),
                    req.getParameter("comment"),
                    Integer.valueOf(req.getParameter("tid"))
            );
            return new SuccessMessage<Boolean>("发表评论成功", tweetService.addTweetComment(tweetComment)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("发表评论失败").getMessage();
        }
    }

    @PostMapping("/comment/{tid}")
    public Object deleteTweetCommentList(@PathVariable("tid") Integer tid) {
        try {
            return new SuccessMessage<Boolean>("删除推文评论成功", tweetService.deleteTweetComment(tid)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取推文评论失败").getMessage();
        }
    }
}
