package com.ssafy.d204.chat.controller;

import com.ssafy.d204.chat.dao.ChatDao;
import com.ssafy.d204.chat.dto.ChatSession;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:8080")
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

    @ApiOperation(value = "본인에게 권한이 있는 방만 검색한다.", response = ChatSession.class)
    @GetMapping("/rooms/user/{userid}")
    @ResponseBody
    public ResponseEntity<?> getMyRoom(@PathVariable int userid) {
        List<ChatSession> ret = null;
        try{
            System.out.println("oh2");
            ret = chatDao.findMyRoom(userid);
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

    // 채팅방 생성
    @ApiOperation(value = "입력한 방의 이름으로 채팅방을 개설한다.(중복가능)", response = ChatSession.class)
    @PostMapping("/room")
    @ResponseBody
    public ResponseEntity<?> createRoom(@RequestBody ChatSession session) {
        System.out.println(session);
        ChatSession ret = ChatSession.create();
        try{
            ret.setFk_client_idx(session.getFk_client_idx());
//            ret.setCreated_at();
            ret.setStatus("OPEN");
            ret.setFk_created_by_idx(session.getFk_created_by_idx());
            chatDao.createChatRoom(ret);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ChatSession>(ret, HttpStatus.OK);
    }

    // 채팅방 정보 아이디에서 받아옴
    @ApiOperation(value = "방의 ID를 가지고 방의 정보를 수신한다.", response = ChatSession.class)
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseEntity<?> getRoomInfo(@PathVariable String roomId) {
        ChatSession ret = null;
        System.out.println(roomId);
        try{
            ret = chatDao.findRoomById(roomId);
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
}