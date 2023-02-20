package com.example.service;

import com.example.dao.AccountMapper;
import com.example.dao.PositionMapper;
import com.example.dao.TradeDetailMapper;
import com.example.pojo.Position;
import com.example.pojo.TradeDetail;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TradeService {

    @Autowired
    TradeDetailMapper tradeDetailMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    PositionMapper positionMapper;

    @Transactional(rollbackFor = Exception.class)
    public int buyCurrent(String bc, String qc, BigDecimal price, BigDecimal size, String account) throws Exception {
        BigDecimal qcBalance = accountMapper.selectCurrencyBalance(qc,account);
        if (price.multiply(size).compareTo(qcBalance) == 1) return 0;
        accountMapper.updateCurrencyBalance(qc, qcBalance.subtract(price.multiply(size)), account);
        List<String> currencies = positionMapper.selectAllCurrencyByAccount(account);
        if (bc.equals("usdt") || bc.equals("btc") || bc.equals("eth") || bc.equals("ht") || bc.equals("husd") || bc.equals("usdd")) {
            accountMapper.updateCurrencyBalance(bc, accountMapper.selectCurrencyBalance(bc, account).add(size), account);
        } else if (currencies.contains(bc)) {
            BigDecimal bcInventory = positionMapper.selectCurrencyInventoryByAccount(bc, account);
            positionMapper.updateCurrency(new Position(null, bc, null, bcInventory.add(size), null, account));
        } else positionMapper.insertCurrency(new Position(null, bc, bc.toUpperCase(), size, null, account));
        tradeDetailMapper.insertTradeDetail(new TradeDetail(null, "buy", bc, qc, price, size, System.currentTimeMillis(), account));
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int sellCurrent(String bc, String qc, BigDecimal price, BigDecimal size, String account) throws Exception {
        List<String> currencies = positionMapper.selectAllCurrencyByAccount(account);
        if (bc.equals("usdt") || bc.equals("btc") || bc.equals("eth") || bc.equals("ht") || bc.equals("husd") || bc.equals("usdd")) {
            BigDecimal bcBalance = accountMapper.selectCurrencyBalance(bc, account);
            if (bcBalance.compareTo(size) == -1) return 0;
            else accountMapper.updateCurrencyBalance(bc, bcBalance.subtract(size), account);
        } else if (currencies.contains(bc)) {
            BigDecimal bcInventory = positionMapper.selectCurrencyInventoryByAccount(bc, account);
            if (bcInventory.compareTo(size) == -1) return 0;
            else positionMapper.updateCurrency(new Position(null, bc, null, bcInventory.subtract(size), null, account));
        } else return -1;
        BigDecimal qcBalance = accountMapper.selectCurrencyBalance(qc,account);
        accountMapper.updateCurrencyBalance(qc, qcBalance.add(price.multiply(size)), account);
        tradeDetailMapper.insertTradeDetail(new TradeDetail(null, "sell", bc, qc, price, size, System.currentTimeMillis(), account));
        return 1;
    }

    public List<TradeDetail> getTradeDetails(String account) {
        return tradeDetailMapper.selectAllTradeDetailsByAccount(account);
    }

    public List<Position> getPositions(String account) {
        return positionMapper.selectPositionByAccount(account);
    }
}
