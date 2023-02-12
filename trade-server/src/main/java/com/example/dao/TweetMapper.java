package com.example.dao;

import com.example.pojo.Tweet;
import com.example.pojo.vo.TweetVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetMapper {
    int insertTweet (Tweet tweet);

    int updateTweetFollowersById (@Param("id") Integer id, @Param("followers") String followers);

    List<TweetVO> selectTweetsByAccount(@Param("account") String account);

    List<TweetVO> selectAllTweets();
}
