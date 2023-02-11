package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.dao.TweetCommentMapper;
import com.example.dao.TweetMapper;
import com.example.pojo.Tweet;
import com.example.pojo.TweetComment;
import com.example.pojo.vo.TweetPlus;
import com.example.service.TweetCommentService;
import com.example.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TweetCommentController {
    @Autowired
    TweetCommentService tweetCommentService;

    @Autowired
    TweetService tweetService;

    @GetMapping("/comment/{tid}")
    public Object getTweetCommentList(@PathVariable("tid") Integer tid) {
        try {
            return new SuccessMessage<List<TweetComment>>("获取推文评论成功", tweetCommentService.getAllTweetComment(tid)).getMessage();
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
            return new SuccessMessage<Boolean>("发表评论成功", tweetCommentService.addTweetComment(tweetComment)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("发表评论失败").getMessage();
        }
    }

    @PostMapping("/comment/{tid}")
    public Object deleteTweetCommentList(@PathVariable("tid") Integer tid) {
        try {
            return new SuccessMessage<Boolean>("删除推文评论成功", tweetCommentService.deleteTweetComment(tid)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取推文评论失败").getMessage();
        }
    }
}
