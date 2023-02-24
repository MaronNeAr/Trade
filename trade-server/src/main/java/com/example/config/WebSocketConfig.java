package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker // 开启WebSocket代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { @Override
public void configureMessageBroker(MessageBrokerRegistry config) {
    // 设置消息代理前缀
    // 即如果消息的前缀是 /topic ，就会将消息转发给消息代理（broker），
    // 再由消息代理将消息广播给当前连接的客户端。
    config.enableSimpleBroker("/topic");

    // 表示配置一个或多个前缀，通过这些前缀过滤出需要被注解方法处理的消息。
    // 例如，前缀为 /app 的 destination 可以通过@MessageMapping注解的方法处理，
    // 而其他 destination （例如 /topic /queue）将被直接交给 broker 处理
    config.setApplicationDestinationPrefixes("/app");
}
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // 表示定义一个前缀为 /chat 的 endPoint，并开启 sockjs 支持，
        // sockjs 可以解决浏览器对 WebSocket 的兼容性问题，
        // 客户端将通过这里配置的 URL 来建立 WebSocket 连接
        registry.addEndpoint("/chat").withSockJS();
    }
}
