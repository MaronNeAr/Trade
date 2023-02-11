package com.example.dao;

import com.example.pojo.Tweet;
import com.example.pojo.TweetComment;
import com.example.pojo.vo.TweetPlus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetCommentMapper {

    List<TweetComment> selectTweetCommentByTid(@Param("tid") Integer tid);

    int insertTweetCommentByTid(TweetComment tweetComment);

    int deleteTweetCommentByTid(Integer tid);

}
