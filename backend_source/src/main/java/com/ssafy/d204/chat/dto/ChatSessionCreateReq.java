package com.ssafy.d204.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionCreateReq {
    int fk_client_idx;
    int fk_created_by_idx;
    String qna_history;
}
