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
    public int buyCurrentCompare(String bc, BigDecimal price) throws Exception {
        String url1 = "https://api-aws.huobi.pro/market/history/kline?symbol=" + bc + "usdt&period=1day";
        System.out.println(url1);
        System.getProperties().put("proxySet", "true");

        //指定代理所在的服务器
        System.getProperties().put("proxyHost", "127.0.0.1");

        //指定代理监听的端口
        System.getProperties().put("proxyPort", "7890");
        // 发送POST请求
        URL url = new URL(url1);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");//修改发送方式
        conn.setRequestProperty("Content-Type",
                "application/json");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setUseCaches(false);
        conn.setDoOutput(true);

        // 获取响应状态
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return 2;
        }
        // 获取响应内容体
        String line, result = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "utf-8"));
        while ((line = in.readLine()) != null) {
            result += line + "\n";
        }
        in.close();
        System.out.println(result);
        JSONObject jsonObject =  JSON.parseObject(result);
        JSONArray jsonArray = null;
        jsonArray = jsonObject.getJSONArray("data");//获取数组
        System.out.println(jsonArray.getJSONObject(0).get("close"));
        BigDecimal price1 = new BigDecimal(String.valueOf(jsonArray.getJSONObject(0).get("close")));
        System.out.println(price1);
        BigDecimal flag1 = price1.subtract(price);
        BigDecimal f = new BigDecimal(0.1);
        System.out.println(f);
        int flag2 = flag1.compareTo(f);
        return flag2;
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
    
     @Transactional(rollbackFor = Exception.class)
    public int sellCurrentCompare(String bc, BigDecimal price) throws Exception {
        String url1 = "https://api-aws.huobi.pro/market/history/kline?symbol=" + bc + "usdt&period=1day";
        System.out.println(url1);
        System.getProperties().put("proxySet", "true");

        //指定代理所在的服务器
        System.getProperties().put("proxyHost", "127.0.0.1");

        //指定代理监听的端口
        System.getProperties().put("proxyPort", "7890");
        // 发送POST请求
        URL url = new URL(url1);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");//修改发送方式
        conn.setRequestProperty("Content-Type",
                "application/json");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setUseCaches(false);
        conn.setDoOutput(true);

        // 获取响应状态
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return 2;
        }
        // 获取响应内容体
        String line, result = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "utf-8"));
        while ((line = in.readLine()) != null) {
            result += line + "\n";
        }
        in.close();
        System.out.println(result);
        JSONObject jsonObject =  JSON.parseObject(result);
        JSONArray jsonArray = null;
        jsonArray = jsonObject.getJSONArray("data");//获取数组
        System.out.println(jsonArray.getJSONObject(0).get("close"));
        BigDecimal price1 = new BigDecimal(String.valueOf(jsonArray.getJSONObject(0).get("close")));
        System.out.println(price1);
        BigDecimal flag1 = price1.subtract(price);
        BigDecimal f = new BigDecimal(0.1);
        System.out.println(f);
        int flag2 = flag1.compareTo(f);
        return flag2;
    }

    public List<TradeDetail> getTradeDetails(String account) {
        return tradeDetailMapper.selectAllTradeDetailsByAccount(account);
    }

    public List<Position> getPositions(String account) {
        return positionMapper.selectPositionByAccount(account);
    }
}
