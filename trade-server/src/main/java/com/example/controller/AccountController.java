package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.pojo.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping("/account")
    public Object getAccount(HttpServletRequest req) {
        try {
            String accountID = req.getParameter("account");
            Account account = accountService.getAccount(accountID);
            if (account == null) {
                accountService.addAccount(accountID);
                return new SuccessMessage<Account>("获取账户成功", accountService.getAccount(accountID)).getMessage();
            } else return new SuccessMessage<Account>("获取账户成功", account).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取账户失败");
        }
    }

    @PostMapping("/recharge")
    public Object rechargeCurrency(HttpServletRequest req){
        try {
            String account = req.getParameter("account");
            BigDecimal recharge = new BigDecimal(req.getParameter("recharge"));
            if (recharge.equals("none")) return new ErrorMessage("请先选择充值余额").getMessage();
            int status = accountService.rechargeByAccount(account, recharge);
            if (status == 1) return new ErrorMessage("您余额不足!" ).getMessage();
            else {
                return new SuccessMessage("充值成功！").getMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("充值失败").getMessage();
        }
    }

    @PostMapping("/cash")
    public Object cashCurrency(HttpServletRequest req){
        try {
            String account = req.getParameter("account");
            BigDecimal cash = new BigDecimal(req.getParameter("cash"));
            if (cash.equals("none")) return new ErrorMessage("请先选择提现余额").getMessage();
            int status = accountService.cashByAccount(account, cash);
            if (status == 1) return new ErrorMessage("您计价币不足!" ).getMessage();
            else {
                return new SuccessMessage("提现成功！").getMessage();
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("提现失败").getMessage();
        }
    }
}
