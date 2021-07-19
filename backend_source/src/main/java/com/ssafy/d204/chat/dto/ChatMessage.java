package com.ssafy.d204.chat.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;

    public enum MessageType{
        JOIN, QUIT, MSG, VIDEO;
    }

}

