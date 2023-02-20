package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.pojo.Position;
import com.example.pojo.TradeDetail;
import com.example.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class TradeController {
    @Autowired
    TradeService tradeService;

    @PostMapping("/buy")
    public Object buyCurrency(HttpServletRequest req) {
        try {
            String bc = req.getParameter("bc");
            String qc = req.getParameter("qc");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            BigDecimal size = new BigDecimal(req.getParameter("size"));
            String account = req.getParameter("account");
            if (bc.equals("none")) return new ErrorMessage("请先选择交易币").getMessage();
            int status = tradeService.buyCurrent(bc, qc, price, size, account);
            if (status == 0) return new ErrorMessage("您没有足够的" + qc.toUpperCase() + "，请先充值").getMessage();
            else return new SuccessMessage("交易成功！").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("交易失败").getMessage();
        }
    }

    @PostMapping("/sell")
    public Object sellCurrency(HttpServletRequest req) {
        try {
            String bc = req.getParameter("bc");
            String qc = req.getParameter("qc");
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            BigDecimal size = new BigDecimal(req.getParameter("size"));
            String account = req.getParameter("account");
            if (bc.equals("none")) return new ErrorMessage("请先选择交易币").getMessage();
            int status = tradeService.sellCurrent(bc, qc, price, size, account);
            if (status == 0) return new ErrorMessage("您没有足够的" + bc).getMessage();
            else return new SuccessMessage("交易成功！").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("交易失败").getMessage();
        }
    }

    @PostMapping("/position")
    public Object getPosition(HttpServletRequest req) {
        try {
            String account = req.getParameter("account");
            return new SuccessMessage<List<Position>>("获取持仓情况成功", tradeService.getPositions(account)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取持仓情况失败").getMessage();
        }
    }

    @PostMapping("/detail")
    public Object getTradeDetails(HttpServletRequest req) {
        try {
            String account = req.getParameter("account");
            return new SuccessMessage<List<TradeDetail>>("获取交易明细成功", tradeService.getTradeDetails(account)).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取交易明细失败").getMessage();
        }
    }
}
