package com.ssafy.d204.chat.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private int pk_idx;
    private String message;
    private int fk_author_idx;
    private String created;
    private boolean deleted = false;
    private int fk_session_idx;
    private MessageType type;

    public enum MessageType{
        JOIN, QUIT, MSG, VID;
    }
}

