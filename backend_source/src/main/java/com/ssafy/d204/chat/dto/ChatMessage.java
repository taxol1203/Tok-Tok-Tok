package com.ssafy.d204.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatMessage {

    private int pk_idx;
    private String message;
    private int fk_author_idx;
    private String created; // 생성날짜.
    private boolean deleted = false; // 삭제되었는지 여부. 만약 true일 경우 유저에게 숨기기
    private String fk_session_id;
    private String type;
//    public enum MessageType{
//        JOIN, QUIT, MSG, VID, NEWR, DELR;
//    }
}

