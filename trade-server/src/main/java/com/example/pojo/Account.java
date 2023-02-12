package com.example.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    Integer id;
    String account;
    BigDecimal balance;
    BigDecimal usdt;
    BigDecimal btc;
    BigDecimal eth;
    BigDecimal ht;
    BigDecimal husd;
    BigDecimal usdd;

    public Account(Integer id, String account, BigDecimal balance, BigDecimal usdt, BigDecimal btc, BigDecimal eth, BigDecimal ht, BigDecimal husd, BigDecimal usdd) {
        this.id = id;
        this.account = account;
        this.balance = balance;
        this.usdt = usdt;
        this.btc = btc;
        this.eth = eth;
        this.ht = ht;
        this.husd = husd;
        this.usdd = usdd;
    }
}
