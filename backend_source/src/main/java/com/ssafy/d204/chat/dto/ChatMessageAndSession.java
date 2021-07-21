package com.ssafy.d204.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ChatMessageAndSession {
    ChatSession session;
    List<ChatMessage> messages;
}
