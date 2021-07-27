package com.ssafy.d204.chat.dto;

import lombok.Data;

@Data
public class ModifyRoomStatusReq {
    ChatSession.SessionState status;
}
