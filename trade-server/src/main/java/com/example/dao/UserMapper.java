package com.example.dao;

import com.example.pojo.User;
import com.example.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User selectUserByAccount(@Param("account") String account);

    int insertUser(@Param("account")String account, @Param("password") String password);

    int updateUser(UserVO userVO);

    List<User> selectAllUsers();
}
