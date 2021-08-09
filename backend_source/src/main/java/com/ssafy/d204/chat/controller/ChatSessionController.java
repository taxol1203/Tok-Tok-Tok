package com.ssafy.d204.chat.controller;

import com.ssafy.d204.chat.dao.ChatDao;
import com.ssafy.d204.chat.dto.AssignRoomRequest;
import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatMessageAndSession;
import com.ssafy.d204.chat.dto.ChatSession;
import com.ssafy.d204.chat.dto.ChatSessionCreateReq;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    try {
      System.out.println("oh2");
      ret = chatDao.findAllRoom();
      for (ChatSession cs : ret) {
        System.out.println("oh");
        System.out.println(cs);
      }
    } catch (Exception e) {
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
    try {
      ret = chatDao.findMyRoom(userid);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<ChatSession>>(ret, HttpStatus.OK);
  }

  // 채팅방 생성
  @ApiOperation(value = "채팅방을 개설한다.", response = ChatSession.class)
  @PostMapping("/room")
  @ResponseBody
  public ResponseEntity<?> createRoom(@RequestBody ChatSessionCreateReq req) {
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
      chatDao.createChatRoom(temp);
      ret = chatDao.findRoomBySessionId(temp.getSession_id());
//            chatDao.pushMessage(new ChatMessage(0,".",ret.getFk_client_idx(),null,false,ret.getSession_id(), ChatMessage.MessageType.JOIN));
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<ChatSession>(ret, HttpStatus.OK);
  }

  @ApiOperation(value = "상담사가 해당 상담을 담당하겠다고 선언한다.", response = ChatSession.class)
  @PutMapping("/room/{sessionId}")
  @ResponseBody
  public ResponseEntity<?> assignRoom(@PathVariable String sessionId,
      @RequestBody AssignRoomRequest req) {
    int result = 0;
    try {
      result = chatDao.assignRoomToMe(sessionId, req.getAdmin_pk_idx());
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (result == 0) {
      return new ResponseEntity<Void>(HttpStatus.CONFLICT); // 409 해당 방이 없거나 혹은 다른 상담사가 이미 가져간 경우
    }
    return new ResponseEntity<Void>(HttpStatus.OK); // 해당 방이 존재하고 아무도 배정을 받지 않았다면 200과 동시에 방 상태가 바뀜
  }

  @ApiOperation(value = "해당 방의 상담을 종료한다.", response = ChatSession.class)
  @DeleteMapping("/room/{sessionId}")
  @ResponseBody
  public ResponseEntity<?> closeRoom(@PathVariable String sessionId,
      @RequestBody AssignRoomRequest req) {
    int result = 0;
    try {
      ChatSession session = chatDao.findRoomBySessionId(sessionId);
      if (session == null) {
        // 해당하는 방이 없다면 204
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
      }
      if (session.getFk_host_idx() != 0 &&
          session.getFk_host_idx() != req.getAdmin_pk_idx()) {
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        // 자기 상담하는 방도 아닌데 닫으려고 하면 403
      }
      result = chatDao.quitChatRoom(sessionId);
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
  @ApiOperation(value = "방의 ID를 가지고 방의 정보를 수신한다.", response = ChatSession.class)
  @GetMapping("/room/{sessionId}")
  @ResponseBody
  public ResponseEntity<?> getRoomInfo(@PathVariable String sessionId) {
    ChatSession ret = null;
    System.out.println(sessionId);
    try {
      ret = chatDao.findRoomBySessionId(sessionId);
      if (ret == null) {
        System.out.println("no room");
      } else {
        System.out.println(ret);
      }
    } catch (Exception e) {
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
    try {
      List<ChatSession> sessions = chatDao.findAllRoom();
      if (sessions.size() == 0) {
        return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
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

  @ApiOperation(value = "해당 상담사에게 배정된 방과 아직 전담 상담사가 없는 방과 메세지를 가져온다.", response = ChatSession.class)
  @GetMapping("/admin/init/{fk_host_idx}")
  @ResponseBody
  public ResponseEntity<?> getAllMessagesAndSessionsByHostIdx(@PathVariable int fk_host_idx) {
    HashMap<String, ChatMessageAndSession> ret = new HashMap<>();
    try {
      List<ChatSession> sessions = chatDao.findAllRoomByFkHostIdx(fk_host_idx);
      if (sessions.size() == 0) {
        return new ResponseEntity<HashMap<String, ChatMessageAndSession>>(ret, HttpStatus.OK);
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

  @ApiOperation(value = "해당 세션ID의 이전 메세지들 가져오기", response = ChatSession.class)
  @GetMapping("/messages/{sessionId}")
  @ResponseBody
  public ResponseEntity<?> getMessagesBySessionId(@PathVariable String sessionId) {
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