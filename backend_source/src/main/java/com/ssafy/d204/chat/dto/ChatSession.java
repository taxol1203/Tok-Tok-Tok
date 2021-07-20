package com.ssafy.d204.chat.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatSession {
    private String pk_idx;
    private String session_id;
    private String created_at; // long으로 바꿀 수 있는지 생각해보기
    private int fk_created_by_idx;
    private int fk_client_idx;
    private int fk_host_idx;
    private int fk_permission_idx;
    String status;
    int unread;

    public static ChatSession create() {
        ChatSession chatSession = new ChatSession();
        chatSession.session_id = UUID.randomUUID().toString();

        return chatSession;
    }
}
