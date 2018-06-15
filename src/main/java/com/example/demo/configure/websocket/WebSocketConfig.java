package com.example.demo.configure.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setHandshakeHandler(new HandshakeHandler()) //注册当前登陆用户
                .setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 全局使用的消息前缀
        registry.setApplicationDestinationPrefixes("/ws");
        //topic 代表发布广播，即群发
        //queue 代表点对点，即发指定用户
        registry.enableSimpleBroker("/queue", "/topic");
        // 点对点使用的订阅前缀
        registry.setUserDestinationPrefix("/user/");
    }
}
