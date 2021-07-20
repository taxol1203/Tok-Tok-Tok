package com.ssafy.d204.chat.dao;

import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatSession;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

//@Repository
public interface ChatDao {
    // ChatSession manage
    public List<ChatSession> findAllRoom();
    public List<ChatSession> findMyRoom(int id);
    public ChatSession findRoomById(String session_id) ;
    public int createChatRoom(ChatSession session);

    // ChatMessage Persistent API
    public int pushMessage(ChatMessage chatMessage);
}