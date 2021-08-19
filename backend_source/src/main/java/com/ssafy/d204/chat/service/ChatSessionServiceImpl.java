package com.ssafy.d204.chat.service;

import com.ssafy.d204.chat.dao.ChatDao;
import com.ssafy.d204.chat.dto.AssignSessionRequest;
import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatMessageAndSession;
import com.ssafy.d204.chat.dto.ChatSession;
import com.ssafy.d204.chat.dto.ChatSessionCreateReq;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    private final ChatDao chatDao;

    public ChatSessionServiceImpl(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public ResponseEntity<?> findAllSessions() {
        List<ChatSession> ret = null;
        try {
//            System.out.println("oh2");
            ret = chatDao.findAllSession();
//            for (ChatSession cs : ret) {
//                System.out.println("oh");
//                System.out.println(cs);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ChatSession>>(ret, HttpStatus.OK);
    }

    public ResponseEntity<?> findMySession(int userid) {
        List<ChatSession> ret = null;
        try {
            ret = chatDao.findMySession(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ChatSession>>(ret, HttpStatus.OK);
    }

    // 채팅방 생성
    public ResponseEntity<?> createChatSession(@RequestBody ChatSessionCreateReq req) {
//        System.out.println(session);
        ChatSession temp = ChatSession.create();
        temp.setFk_client_idx(req.getFk_client_idx());
        temp.setFk_created_by_idx(req.getFk_created_by_idx());
        temp.setQna_history(req.getQna_history());
        ChatSession ret = null;
        try {
            temp.setFk_client_idx(temp.getFk_client_idx());
//            ret.setCreated_at();
            temp.setStatus("OPEN");
            temp.setFk_created_by_idx(temp.getFk_created_by_idx());
            temp.setQna_history(temp.getQna_history());
            chatDao.createChatSession(temp);
            ret = chatDao.findSessionBySessionId(temp.getSession_id());
//            chatDao.pushMessage(new ChatMessage(0,".",ret.getFk_client_idx(),null,false,ret.getSession_id(), ChatMessage.MessageType.JOIN));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ChatSession>(ret, HttpStatus.OK);
    }

    public ResponseEntity<?> assignSessionToMe(String sessionId,
        AssignSessionRequest req) {
        int result = 0;
        try {
            result = chatDao.assignSessionToMe(sessionId, req.getAdmin_pk_idx());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result == 0) {
            return new ResponseEntity<Void>(
                HttpStatus.CONFLICT); // 409 해당 방이 없거나 혹은 다른 상담사가 이미 가져간 경우
        }
        return new ResponseEntity<Void>(
            HttpStatus.OK); // 해당 방이 존재하고 아무도 배정을 받지 않았다면 200과 동시에 방 상태가 바뀜
    }

    public ResponseEntity<?> closeSession(String sessionId, AssignSessionRequest req) {
        int result = 0;
        try {
            ChatSession session = chatDao.findSessionBySessionId(sessionId);
            if (session == null) {
                // 해당하는 방이 없다면 204
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
            if (session.getFk_host_idx() != 0 &&
                session.getFk_host_idx() != req.getAdmin_pk_idx()) {
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
                // 자기 상담하는 방도 아닌데 닫으려고 하면 403
            }
            result = chatDao.quitChatSession(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result == 0) {
            new ResponseEntity<Void>(HttpStatus.ALREADY_REPORTED);
            // 이미 방이 닫혔다면 208
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
        // 성공시 200
    }


    // 채팅방 정보 아이디에서 받아옴
    public ResponseEntity<?> getSessionInfo(String sessionId) {
        ChatSession ret = null;
//        System.out.println(sessionId);
        try {
            ret = chatDao.findSessionBySessionId(sessionId);
//            if (ret == null) {
//                System.out.println("no room");
//            } else {
//                System.out.println(ret);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ChatSession>(ret, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllMessagesAndSessions() {
        HashMap<String, ChatMessageAndSession> ret = new HashMap<>();
        try {
            List<ChatSession> sessions = chatDao.findAllSession();
            if (sessions.size() == 0) {
                return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret,
                    HttpStatus.OK);
            }
            for (ChatSession session : sessions) {
                ret.put(session.getSession_id(),
                    new ChatMessageAndSession(session, new ArrayList<ChatMessage>()));
            }
            sessions = null; // for gc
            List<ChatMessage> messages = chatDao.getAllMessages();
            for (ChatMessage message : messages) {
                ret.get(message.getFk_session_id()).getMessages().add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllMessagesAndSessionsByHostIdx(int fk_host_idx) {
        HashMap<String, ChatMessageAndSession> ret = new HashMap<>();
        try {
            List<ChatSession> sessions = chatDao.findAllSessionByFkHostIdx(fk_host_idx);
            if (sessions.size() == 0) {
                return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret,
                    HttpStatus.OK);
            }
            for (ChatSession session : sessions) {
                ret.put(session.getSession_id(),
                    new ChatMessageAndSession(session, new ArrayList<ChatMessage>()));
            }
            sessions = null; // for gc
            List<ChatMessage> messages = chatDao.getAllMessages();
            for (ChatMessage message : messages) {
                if (!ret.containsKey(message.getFk_session_id())) {
                    continue;
                }
                ret.get(message.getFk_session_id())
                    .getMessages()
                    .add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
    }

    public ResponseEntity<?> getMessagesBySessionId(String sessionId) {
        List<ChatMessage> ret;
        try {
            ret = chatDao.getMessagesBySessionId(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ret.size() == 0) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ChatMessage>>(ret, HttpStatus.OK);
    }

}
