package com.example.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeDetail {
    Integer id;
    String type;
    String baseCurrency;
    String quoteCurrency;
    BigDecimal price;
    BigDecimal size;
    Long time;
    String account;

    public TradeDetail(Integer id, String type, String baseCurrency, String quoteCurrency, BigDecimal price, BigDecimal size, Long time, String account) {
        this.id = id;
        this.type = type;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.price = price;
        this.size = size;
        this.time = time;
        this.account = account;
    }
}
