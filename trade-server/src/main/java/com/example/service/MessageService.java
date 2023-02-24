package com.example.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        System.out.println("正在发送信息" + message);
        rabbitTemplate.convertAndSend("message-exchange", "message.order", message);
    }
}
