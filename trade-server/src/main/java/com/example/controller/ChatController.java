package com.example.controller;

import com.example.config.common.ErrorMessage;
import com.example.config.common.SuccessMessage;
import com.example.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    public Object chat(HttpServletRequest req) {
        try {
            String prompt = req.getParameter("prompt");
            return new SuccessMessage(chatService.completion(prompt)).getMessage();
        } catch (IOException e) {
            System.out.println(e);
            return new ErrorMessage("获取chat信息失败").getMessage();
        }
    }
}
