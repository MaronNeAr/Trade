package com.example.dao;

import com.example.pojo.Tweet;
import com.example.pojo.vo.TweetPlus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetMapper {
    int insertTweet (Tweet tweet);

    int updateTweetFollowersById (@Param("id") Integer id, @Param("followers") String followers);

    List<TweetPlus> selectTweetsByAccount(@Param("account") String account);

    List<TweetPlus> selectAllTweets();
}
