//package com.ssafy.d204.chat.config;
//
//import com.ssafy.d204.chat.handler.SignalingSocketHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//public class WebRtcSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//        webSocketHandlerRegistry
//                // handle on "/signal" endpoint
//                .addHandler(signalingSocketHandler(), "/signal")
//                // Allow cross origins
//                .setAllowedOrigins("*");
//    }
//
//    @Bean
//    public WebSocketHandler signalingSocketHandler() {
//        return new SignalingSocketHandler();
//    }
//}