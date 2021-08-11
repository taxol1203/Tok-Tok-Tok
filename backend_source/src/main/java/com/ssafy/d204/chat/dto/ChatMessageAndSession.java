package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatMessageAndSession {

    ChatSession session;
    List<ChatMessage> messages;
}
