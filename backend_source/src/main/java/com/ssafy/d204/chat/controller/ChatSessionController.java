package com.ssafy.d204.chat.controller;

import com.ssafy.d204.chat.dto.AssignRoomRequest;
import com.ssafy.d204.chat.dao.ChatDao;
import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatMessageAndSession;
import com.ssafy.d204.chat.dto.ChatSession;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.reactive.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/api/chat")
public class ChatSessionController {
//    @Autowired
    private final ChatDao chatDao;
//
//    ChatSessionController(@Autowired ChatDao chatDao){
//        super();
//        this.chatDao = chatDao;
//    }
    // 모든 채팅방 목록 보기
    @ApiOperation(value = "현재 개설중인 모든 방의 목록을 가져온다.", response = ChatSession.class)
    @GetMapping("/rooms/all")
    @ResponseBody
    public ResponseEntity<?> getAllRoom() {
        List<ChatSession> ret = null;
        try{
            System.out.println("oh2");
            ret = chatDao.findAllRoom();
            for(ChatSession cs : ret){
                System.out.println("oh");
                System.out.println(cs);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ChatSession>>(ret, HttpStatus.OK);
    }

    @ApiOperation(value = "(유저측)본인에게 권한이 있는 방만 검색한다.", response = ChatSession.class)
    @GetMapping("/rooms/user/{userid}")
    @ResponseBody
    public ResponseEntity<?> getMyRoom(@PathVariable int userid) {
        List<ChatSession> ret = null;
        try{
            ret = chatDao.findMyRoom(userid);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ChatSession>>(ret, HttpStatus.OK);
    }

    // 채팅방 생성
    @ApiOperation(value = "채팅방을 개설한다.", response = ChatSession.class)
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody ChatSession session) {
        System.out.println(session);
        ChatSession ret = ChatSession.create();
        try{
            ret.setFk_client_idx(session.getFk_client_idx());
//            ret.setCreated_at();
            ret.setStatus(ChatSession.SessionState.OPEN);
            ret.setFk_created_by_idx(session.getFk_created_by_idx());
            chatDao.createChatRoom(ret);
//            chatDao.pushMessage(new ChatMessage(0,".",ret.getFk_client_idx(),null,false,ret.getSession_id(), ChatMessage.MessageType.JOIN));
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ChatSession>(ret, HttpStatus.OK);
    }
    @ApiOperation(value = "상담사가 해당 상담을 담당하겠다고 선언한다.", response = ChatSession.class)
    @PutMapping("/room/{sessionId}")
    @ResponseBody
    public ResponseEntity<?> assignRoom(@PathVariable String sessionId, @RequestBody AssignRoomRequest request) {
        int result = 0;
        request.setSessionId(sessionId);
        try{
            result = chatDao.assignRoomToMe(request);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result == 0){
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @ApiOperation(value = "해당 방의 상담을 종료한다.", response = ChatSession.class)
    @DeleteMapping("/room/{sessionId}")
    @ResponseBody
    public ResponseEntity<?> closeRoom(@PathVariable String sessionId, @RequestBody int admin_pk_idx) {
        int result = 0;
        try{
            ChatSession session = chatDao.findRoomBySessionId(sessionId);
            if(session == null){
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
            if(session.getFk_host_idx() != 0 &&
                    session.getFk_host_idx()!= admin_pk_idx){
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            }
            result = chatDao.quitChatRoom(sessionId);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(result == 0){
            new ResponseEntity<Void>(HttpStatus.ALREADY_REPORTED);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }



        // 채팅방 정보 아이디에서 받아옴
    @ApiOperation(value = "방의 ID를 가지고 방의 정보를 수신한다.", response = ChatSession.class)
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<?> getRoomInfo(@PathVariable String roomId) {
        ChatSession ret = null;
        System.out.println(roomId);
        try{
            ret = chatDao.findRoomBySessionId(roomId);
            if(ret == null){
                System.out.println("no room");
            }else{
                System.out.println(ret);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ChatSession>(ret, HttpStatus.OK);
    }

    @ApiOperation(value = "모든 채팅방과 모든 메세지를 가져온다.", response = ChatSession.class)
    @GetMapping("/admin/init")
    @ResponseBody
    public ResponseEntity<?> getAllMessagesAndSessions() {
        HashMap<String, ChatMessageAndSession> ret = new HashMap<>();
        try{
            List<ChatSession> sessions = chatDao.findAllRoom();
            if(sessions.size() == 0){
                return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
            }
            for(ChatSession session : sessions){
                ret.put(session.getSession_id(), new ChatMessageAndSession(session,new ArrayList<ChatMessage>()));
            }
            sessions = null; // for gc
            List<ChatMessage> messages = chatDao.getAllMessages();
            for(ChatMessage message : messages){
                ret.get(message.getFk_session_id()).getMessages().add(message);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
    }
    @ApiOperation(value = "해당 상담사에게 배정된 방과 아직 전담 상담사가 없는 방과 메세지를 가져온다.", response = ChatSession.class)
    @GetMapping("/admin/init/{fk_host_idx}")
    @ResponseBody
    public ResponseEntity<?> getAllMessagesAndSessionsByHostIdx(@PathVariable int fk_host_idx) {
        HashMap<String, ChatMessageAndSession> ret = new HashMap<>();
        try{
            List<ChatSession> sessions = chatDao.findAllRoomByFkHostIdx(fk_host_idx);
            if(sessions.size() == 0){
                return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
            }
            for(ChatSession session : sessions){
                ret.put(session.getSession_id(), new ChatMessageAndSession(session,new ArrayList<ChatMessage>()));
            }
            sessions = null; // for gc
            List<ChatMessage> messages = chatDao.getAllMessages();
            for(ChatMessage message : messages){
                if(!ret.containsKey(message.getFk_session_id())){
                    continue;
                }
                ret.get(message.getFk_session_id())
                        .getMessages()
                        .add(message);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
    }
    @ApiOperation(value = "해당 세션ID의 이전 메세지들 가져오기", response = ChatSession.class)
    @GetMapping("/messages/{sessionId}")
    @ResponseBody
    public ResponseEntity<?> getMessagesBySessionId(@PathVariable String sessionId) {
        List<ChatMessage> ret;
        try{
            ret = chatDao.getMessagesBySessionId(sessionId);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<ChatMessage>>(ret, HttpStatus.OK);
    }

}