package com.example.service;

import com.example.dao.AccountMapper;
import com.example.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountMapper accountMapper;

    public Account getAccount(String account) {
        return  accountMapper.selectAccount(account);
    }

    public boolean addAccount(String account) {return accountMapper.insertAccount(account) > 0;}

    public BigDecimal getBalance(String account) {
        return accountMapper.selectBalanceByAccount(account);
    }

    public BigDecimal getAccountUsdt(String account) {
        return accountMapper.selectUsdtByAccount(account);
    }

    public int updateAccountUsdt(String account, BigDecimal usdt) {
        accountMapper.updateUsdtByRecharge(account,usdt);
        return 1;
    }

    public int updateAccountBalance(String account, BigDecimal balance) {
        accountMapper.updateBalanceByRecharge(account,balance);
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int rechargeByAccount(String account, BigDecimal recharge) throws Exception {
        BigDecimal usdt = getAccountUsdt(account);
        BigDecimal balance = getBalance(account);
        int flag = recharge.compareTo(balance);
        if (flag == 1 ) return 1;
        BigDecimal newusdt = usdt.add(recharge);
        BigDecimal newbalance = balance.subtract(recharge);
        updateAccountBalance(account, newbalance);
        updateAccountUsdt(account, newusdt);
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public int cashByAccount(String account, BigDecimal cash) throws Exception {
        BigDecimal usdt = getAccountUsdt(account);
        BigDecimal balance = getBalance(account);
        int flag = cash.compareTo(usdt);
        if (flag == 1 ) return 1;
        BigDecimal newbalance = balance.add(cash);
        BigDecimal newusdt = usdt.subtract(cash);
        updateAccountBalance(account, newbalance);
        updateAccountUsdt(account, newusdt);
        return 0;
    }
}
