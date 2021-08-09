package com.ssafy.d204.chat.controller;

import com.ssafy.d204.chat.dto.AssignSessionRequest;
import com.ssafy.d204.chat.dto.ChatMessage;
import com.ssafy.d204.chat.dto.ChatMessageAndSession;
import com.ssafy.d204.chat.dto.ChatSession;
import com.ssafy.d204.chat.dto.ChatSessionCreateReq;
import com.ssafy.d204.chat.service.ChatSessionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    static final String SESSION_ID_EXAMPLE = "SOME-SESSION-ID";
    static final String PK_IDX_EXAMPLE = "1";

    //    @Autowired
    private final ChatSessionService chatSessionService;

    // 모든 채팅방 목록 보기
    @ApiOperation(value = "현재 개설중인 모든 방의 목록을 가져온다.")
    @GetMapping(value= "/rooms/all", produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "전체 방 목록 조회 성공", response = ChatSession.class, responseContainer = "List"),
        @ApiResponse(code = 500, message = "DB 오류", response = Void.class),
    })
    public ResponseEntity<?> findAllSessions() {
        return chatSessionService.findAllSessions();
    }

    @ApiOperation(value = "(유저측)본인에게 권한이 있는 방만 검색한다.", produces = "application/json")
    @GetMapping(value = "/rooms/user/{userid}", produces = "application/json")
    @ResponseBody
    @ApiImplicitParam(name = "userId", value = "유저의 pk_idx", required = true, example = PK_IDX_EXAMPLE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "방 검색 완료"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> findMySession(@PathVariable int userid) {
        return chatSessionService.findMySession(userid);
//        List<ChatSession> ret = null;
//        try {
//            ret = chatDao.findMySession(userid);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<List<ChatSession>>(ret, HttpStatus.OK);
    }

    // 채팅방 생성
    @ApiOperation(value = "채팅방을 개설한다.")
    @PostMapping(value = "/room", produces = "application/json")
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "채팅방 개설 성공"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> createChatSession(@RequestBody ChatSessionCreateReq req) {
        return chatSessionService.createChatSession(req);
    }

    @ApiOperation(value = "상담사가 해당 상담을 담당하겠다고 선언한다.")
    @PutMapping("/room/{sessionId}")
    @ApiImplicitParam(name = "sessionId", value = "세션 아이디", required = true, example = SESSION_ID_EXAMPLE)
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "상담사 배정 성공"),
        @ApiResponse(code = 409, message = "해당 세션이 존재하지 않거나 다른 상담사가 이미 배정된 경우"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> assignSessionToMe(@PathVariable String sessionId,
        @RequestBody AssignSessionRequest req) {
        return chatSessionService.assignSessionToMe(sessionId, req);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "상담사 배정 성공"),
        @ApiResponse(code = 208, message = "이미 상담종료된 경우"),
        @ApiResponse(code = 403, message = "자기에게 배정된 방이 아닌 경우에 상담 종료 시 거부"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    @ApiOperation(value = "해당 방의 상담을 종료한다.")
    @DeleteMapping("/room/{sessionId}")
    @ApiImplicitParam(name = "sessionId", value = "세션 아이디", required = true, example = SESSION_ID_EXAMPLE)
    @ResponseBody
    public ResponseEntity<?> closeSession(@PathVariable String sessionId,
        @RequestBody AssignSessionRequest req) {
        return chatSessionService.closeSession(sessionId, req);
    }


    // 채팅방 정보 아이디에서 받아옴
    @ApiOperation(value = "방의 ID를 가지고 방의 정보를 수신한다.", response = ChatSession.class)
    @GetMapping("/room/{sessionId}")
    @ApiImplicitParam(name = "sessionId", value = "세션 아이디", required = true, example = SESSION_ID_EXAMPLE)
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "방 정보 조회 성공"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> getSessionInfo(@PathVariable String sessionId) {
        return chatSessionService.getSessionInfo(sessionId);
    }


    @ApiOperation(value = "모든 채팅방과 모든 메세지를 가져온다.", response = ChatMessageAndSession.class)
    @GetMapping("/admin/init")
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "모든 메세지와 방 가져오기 성공"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> getAllMessagesAndSessions() {
        return chatSessionService.getAllMessagesAndSessions();
    }

    @ApiOperation(value = "해당 상담사에게 배정된 방과 아직 전담 상담사가 없는 방과 메세지를 가져온다.", response = ChatMessageAndSession.class)
    @GetMapping("/admin/init/{fk_host_idx}")
    @ApiImplicitParam(name = "fk_host_idx", value = "현재 상담사의 pk_idx", required = true, example = PK_IDX_EXAMPLE)
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "모든 메세지와 방 가져오기 성공"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> getAllMessagesAndSessionsByHostIdx(@PathVariable int fk_host_idx) {
        return chatSessionService.getAllMessagesAndSessionsByHostIdx(fk_host_idx);
    }

    @ApiOperation(value = "해당 세션ID의 이전 메세지들 가져오기", response = ChatMessage.class)
    @GetMapping("/messages/{sessionId}")
    @ApiImplicitParam(name = "sessionId", value = "세션 아이디", required = true, example = SESSION_ID_EXAMPLE)
    @ResponseBody
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "메세지 가져오기 성공"),
        @ApiResponse(code = 204, message = "해당 방 메세지 없음"),
        @ApiResponse(code = 500, message = "DB 오류"),
    })
    public ResponseEntity<?> getMessagesBySessionId(@PathVariable String sessionId) {
        return chatSessionService.getMessagesBySessionId(sessionId);
    }
}
