package com.ssafy.d204.chat.dao;

import com.ssafy.d204.chat.dto.AssignRoomRequest;
import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatSession;

import java.util.*;

//@Repository
public interface ChatDao {
    // ChatSession management
    public List<ChatSession> findAllRoom();
    public List<ChatSession> findAllRoomByFkHostIdx(int fk_host_idx);
    public List<ChatSession> findMyRoom(int id);
    public ChatSession findRoomBySessionId(String session_id);

    public int createChatRoom(ChatSession session);

    public int assignRoomToMe(AssignRoomRequest request);
    public int quitChatRoom(String sessionId);

    // ChatMessage Persistent API
    public int pushMessage(ChatMessage chatMessage);
    public List<ChatMessage> getAllMessages();
    public List<ChatMessage> getMessagesBySessionId(String session_pk_idx);
}