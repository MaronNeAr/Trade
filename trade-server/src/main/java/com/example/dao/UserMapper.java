package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User selectUserByAccount(@Param("account") String account);

    int insertUser(@Param("account")String account, @Param("password") String password);

    List<User> selectAllUsers();
}
