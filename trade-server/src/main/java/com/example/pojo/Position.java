package com.example.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Position {
    Integer id;
    String currencyCode;
    String displayName;
    BigDecimal inventory;
    BigDecimal price;
    String account;

    public Position(Integer id, String currencyCode, String displayName, BigDecimal inventory, BigDecimal price, String account) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.displayName = displayName;
        this.inventory = inventory;
        this.price = price;
        this.account = account;
    }
}
