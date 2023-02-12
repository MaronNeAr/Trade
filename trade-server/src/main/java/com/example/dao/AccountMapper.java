package com.example.dao;

import com.example.pojo.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository

public interface AccountMapper {
    Account selectAccountBlance(@Param("account") String account);

    BigDecimal selectCurrencyBalance(@Param("qc") String qc, @Param("account") String account);

    int updateCurrencyBalance(@Param("qc") String qc, @Param("value") BigDecimal value, @Param("account") String account);
}
