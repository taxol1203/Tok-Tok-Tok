package com.ssafy.d204.chat.controller;

import com.ssafy.d204.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
//@NoArgsConstructor
@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/chat")
public class ChatController {
    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")

    // /send로 메시지를 반환합니다.
    @SendTo("/send")

    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 ChatMessage를 1) 인자값, 2) 반환값으로 사용합니다.
    public ChatMessage SocketHandler(ChatMessage chatMessage) {
//        // vo에서 getter로 userName을 가져옵니다.
        String sender = chatMessage.getSender();
//        // vo에서 setter로 content를 가져옵니다.
        String message = chatMessage.getMessage();

        // 생성자로 반환값을 생성합니다.
//        ChatMessage result = new ChatMessage();
        // 반환
        System.out.println(chatMessage);
        ChatMessage ret = new ChatMessage();
        ret.setMessage(message);
        ret.setSender(sender);
        return ret;
    }
}