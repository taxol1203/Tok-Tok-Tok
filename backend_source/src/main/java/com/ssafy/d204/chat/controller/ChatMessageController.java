package com.ssafy.d204.chat.controller;

import com.ssafy.d204.chat.dao.ChatDao;
import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatSession;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/chat")
public class ChatMessageController {
    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatDao chatDao;

    @MessageMapping("/receive/{sessionId}")  // 클라이언트가 메세지를 보내는 주소
    @SendTo("/send/{sessionId}") // 클라이언트가 listen 하는 주소
    public ChatMessage SocketHandler(ChatMessage chatMessage, @DestinationVariable String sessionId) {
        System.out.println(sessionId);
        System.out.println(chatMessage);
        // 채팅 후처리, REDIS등 적용은 여기에서
//        this.simpMessagingTemplate.convertAndSend("/send/"+sessionId, chatMessage);
        try{
            chatDao.pushMessage(chatMessage);
        }catch(Exception e){
            System.out.println("err");
            e.printStackTrace();
            // do something when error occured.
        }

        this.simpMessagingTemplate.convertAndSend("/send/admin", chatMessage);
//        this.simpMessagingTemplate.convertAndSend("/send/"+sessionId, chatMessage);
        return chatMessage;
//        return null;
    }


}