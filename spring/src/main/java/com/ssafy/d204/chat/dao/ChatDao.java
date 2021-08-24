package com.ssafy.d204.chat.dao;

import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatSession;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatDao {

    // ChatSession management
    List<ChatSession> findAllSession();
    List<ChatSession> findAllSessionByFkHostIdx(int fk_host_idx);
    List<ChatSession> findMySession(int sessionId);
    ChatSession findSessionBySessionId(String session_id);

    int createChatSession(ChatSession session);
    int assignSessionToMe(String sessionId, int admin_pk_idx);
    int quitChatSession(String sessionId);

    // ChatMessage Persistent API
    int pushMessage(ChatMessage chatMessage);
    List<ChatMessage> getAllMessages();
    List<ChatMessage> getMessagesBySessionId(String session_pk_idx);
}