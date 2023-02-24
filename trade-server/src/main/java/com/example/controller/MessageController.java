package com.example.controller;


import com.example.config.common.SuccessMessage;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/message")
    public Object handle(HttpServletRequest req) {
        String message = req.getParameter("message");
        System.out.println("服务启动");
        messageService.sendMessage(message);
        return new SuccessMessage("发送消息成功").getMessage();
    }
}
