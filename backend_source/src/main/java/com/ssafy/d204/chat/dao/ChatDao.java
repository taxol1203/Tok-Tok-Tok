package com.ssafy.d204.chat.dao;

import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatSession;
import java.util.List;

//@Repository
public interface ChatDao {

  // ChatSession management
  List<ChatSession> findAllRoom();

  List<ChatSession> findAllRoomByFkHostIdx(int fk_host_idx);

  List<ChatSession> findMyRoom(int sessionId);

  ChatSession findRoomBySessionId(String session_id);

  int createChatRoom(ChatSession session);

  int assignRoomToMe(String sessionId, int admin_pk_idx);

  int quitChatRoom(String sessionId);

  // ChatMessage Persistent API
  int pushMessage(ChatMessage chatMessage);

  List<ChatMessage> getAllMessages();

  List<ChatMessage> getMessagesBySessionId(String session_pk_idx);
}