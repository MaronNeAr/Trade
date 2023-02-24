package com.example.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @RabbitListener(queues = "message-queue")
    public void receive(String message) {
        System.out.println("已完成" + message + "的发送");
    }

}
