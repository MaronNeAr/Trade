package com.example;

import com.example.service.ChatService;
import com.example.service.RedisService;
import com.example.service.SmsService;
import com.example.service.TradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeApplicationTests {
    @Autowired
    private TradeService tradeService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ChatService chatService;

    @Test
    public void Test01() throws Exception {
        tradeService.buyCurrent("btc", "usdt",  new BigDecimal("200.133435"), new BigDecimal("1.09"), "marlon1475");
    }

    @Test
    public void Test02() throws Exception {
        tradeService.buyCurrent("luna", "usdt",  new BigDecimal("200.133435"), new BigDecimal("1.09"), "marlon1475");
    }

    @Test
    public void Test03() throws Exception {
        System.out.println(tradeService.sellCurrent("btc", "usdt",  new BigDecimal("200.133435"), new BigDecimal("1.09"), "marlon1475"));
    }

    @Test
    public void Test04() throws Exception {
        tradeService.sellCurrent("luna", "usdt",  new BigDecimal("200.133435"), new BigDecimal("1.09"), "marlon1475");
    }

    @Test
    public void Test05() {
        redisService.set("key", "val");
        System.out.println(redisService.get("key"));
    }

    @Test
    public void test06() {
//        smsService.sendSmsMessage("+8613299531475", "999999");
    }

    @Test
    public void test07() throws IOException {
        chatService.completion("哈哈");
    }
}
