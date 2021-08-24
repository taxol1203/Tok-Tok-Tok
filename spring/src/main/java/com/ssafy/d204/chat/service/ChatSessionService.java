package com.ssafy.d204.chat.service;

import com.ssafy.d204.chat.dto.AssignSessionRequest;
import com.ssafy.d204.chat.dto.ChatSessionCreateReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ChatSessionService {

    public ResponseEntity<?> findAllSessions();

    public ResponseEntity<?> findMySession(int userid);

    public ResponseEntity<?> createChatSession(ChatSessionCreateReq req);

    public ResponseEntity<?> assignSessionToMe(String sessionId, AssignSessionRequest req);

    public ResponseEntity<?> closeSession(String sessionId, AssignSessionRequest req);

    public ResponseEntity<?> getSessionInfo(String sessionId);

    public ResponseEntity<?> getAllMessagesAndSessions();

    public ResponseEntity<?> getAllMessagesAndSessionsByHostIdx(int fk_host_idx);

    public ResponseEntity<?> getMessagesBySessionId(String sessionId);

}