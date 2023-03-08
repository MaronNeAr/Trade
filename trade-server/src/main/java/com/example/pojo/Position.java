package com.example.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Position {
    private Integer id;
    private String currencyCode;
    private String displayName;
    private BigDecimal inventory;
    private BigDecimal price;
    private String account;

    public Position(Integer id, String currencyCode, String displayName, BigDecimal inventory, BigDecimal price, String account) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.displayName = displayName;
        this.inventory = inventory;
        this.price = price;
        this.account = account;
    }
}
