package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DataController {
    @Autowired
    FileService fileService;

    @PostMapping("/currency")
    public Object setNewCurrencyData(HttpServletRequest req) {
        try {
            String content = req.getParameter("content");
            fileService.writeToFile("data/currency.data", content);
            return new SuccessMessage("上传币种数据成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("上传币种数据失败").getMessage();
        }
    }

    @GetMapping("/currency")
    public Object getNewCurrencyData() {
        try {
            String content = fileService.readFromFile("data/currency.data");
            return new SuccessMessage<String>("获取币种数据成功", content).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取币种数据失败").getMessage();
        }
    }

    @PostMapping("/tickers")
    public Object setTickers(HttpServletRequest req) {
        try {
            String content = req.getParameter("content");
            fileService.writeToFile("data/tickers.data", content);
            return new SuccessMessage("上传币种价格成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("上传币种价格失败").getMessage();
        }
    }

    @GetMapping("/tickers")
    public Object getTickers() {
        try {
            String content = fileService.readFromFile("data/tickers.data");
            return new SuccessMessage<String>("获取币种价格成功", content).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取币种价格失败").getMessage();
        }
    }

    @PostMapping("/k-chart/{sc}")
    public Object setChartData(HttpServletRequest req, @PathVariable String sc) {
        try {
            String content = req.getParameter("content");
            fileService.writeToFile("data/k-chart/" + sc + ".data", content);
            return new SuccessMessage("上传日K数据成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("上传日K数据失败").getMessage();
        }
    }

    @GetMapping("/k-chart/{sc}")
    public Object getChartData(@PathVariable String sc) {
        try {
            String content = fileService.readFromFile("data/k-chart/" + sc + ".data");
            return new SuccessMessage<String>("获取日K数据成功", content).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取日K数据失败").getMessage();
        }
    }

    @PostMapping("/m-depth/{sc}")
    public Object setDepthData(HttpServletRequest req, @PathVariable String sc) {
        try {
            String content = req.getParameter("content");
            fileService.writeToFile("data/m-depth/" + sc + ".data", content);
            return new SuccessMessage("上传市场深度数据成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("上传市场深度数据失败").getMessage();
        }
    }

    @GetMapping("/m-depth/{sc}")
    public Object getDepthData(@PathVariable String sc) {
        try {
            String content = fileService.readFromFile("data/m-depth/" + sc + ".data");
            return new SuccessMessage<String>("获取市场深度数据成功", content).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取市场深度数据失败").getMessage();
        }
    }

    @PostMapping("/ticker/{sc}")
    public Object setTickerData(HttpServletRequest req, @PathVariable String sc) {
        try {
            String content = req.getParameter("content");
            fileService.writeToFile("data/ticker/" + sc + ".data", content);
            return new SuccessMessage("上传聚合行情数据成功").getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("上传聚合行情数据失败").getMessage();
        }
    }

    @GetMapping("/ticker/{sc}")
    public Object getTickerData(@PathVariable String sc) {
        try {
            String content = fileService.readFromFile("data/ticker/" + sc + ".data");
            return new SuccessMessage<String>("获取聚合行情数据成功", content).getMessage();
        } catch (Exception e) {
            System.out.println(e);
            return new ErrorMessage("获取聚合行情数据失败").getMessage();
        }
    }
}
