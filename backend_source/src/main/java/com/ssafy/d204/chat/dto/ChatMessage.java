package com.ssafy.d204.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ChatMessage {
    private int pk_idx;
    private String message;
    private int fk_author_idx;
    private String created;
    private boolean deleted = false;
    private String fk_session_id;
    private MessageType type;

    public enum MessageType{
        JOIN, QUIT, MSG, VID, NEWR, DELR;
    }
}

