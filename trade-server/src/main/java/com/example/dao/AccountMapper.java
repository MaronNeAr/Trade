package com.example.dao;

import com.example.pojo.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository

public interface AccountMapper {
    Account selectAccount(@Param("account") String account);

    int insertAccount(@Param("account") String account);

    BigDecimal selectCurrencyBalance(@Param("qc") String qc, @Param("account") String account);

    int updateCurrencyBalance(@Param("qc") String qc, @Param("value") BigDecimal value, @Param("account") String account);

    BigDecimal selectUsdtByAccount(@Param("account") String account);

    BigDecimal selectBalanceByAccount(@Param("account") String account);

    int updateUsdtByRecharge(@Param("account") String account, @Param("usdt") BigDecimal usdt);

    int updateBalanceByRecharge(@Param("account") String account, @Param("balance") BigDecimal balance);

}
