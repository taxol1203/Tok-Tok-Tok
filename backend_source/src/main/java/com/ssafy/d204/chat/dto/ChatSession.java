package com.ssafy.d204.chat.dto;

import lombok.Data;

import java.util.List;
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
    private String qna_history;
    String status;
    int unread;
//    SessionState status;
//    public enum SessionState{
//        OPEN, // 막 상담이 열린 상태
//        END,  // 상담 종료
//        LIVE // 상담이 진행중임. 전담 상담사 배정된 상태
//    }
    public static ChatSession create() {
        ChatSession chatSession = new ChatSession();
        chatSession.session_id = UUID.randomUUID().toString();

        return chatSession;
    }
}
