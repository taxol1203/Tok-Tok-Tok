package com.ssafy.d204;

import com.mysql.cj.Session;
import com.ssafy.d204.api.controller.QnAController;
import com.ssafy.d204.chat.controller.ChatSessionController;
import com.ssafy.d204.chat.dto.AssignRoomRequest;
import com.ssafy.d204.chat.dto.ChatSession;
import com.ssafy.d204.chat.dto.ChatSessionCreateReq;
import com.ssafy.d204.db.entity.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.hamcrest.*;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ChatSessionControllerTest {
	@Autowired
	ChatSessionController csc;

	@Test
	void createChatSession(){
		ResponseEntity<?> roomResponse = csc.getAllRoom();
		Assertions.assertEquals(200,roomResponse.getStatusCodeValue());
		List<ChatSession> sessionsBefore = (List<ChatSession>) roomResponse.getBody();

		ChatSession createSessionRequest = new ChatSession();
		createSessionRequest.setFk_created_by_idx(1);
		createSessionRequest.setFk_client_idx(1);

		ChatSession newSession =
				(ChatSession) csc.createRoom(new ChatSessionCreateReq(1,1, "/")).getBody();
//		MatcherAssert(sessionsBefore.size(), isGreater);
		ResponseEntity<?> roomResponse2 = csc.getAllRoom();
		Assertions.assertEquals(200,roomResponse2.getStatusCodeValue());
		List<ChatSession> sessionsAfter =
				(List<ChatSession>) roomResponse2.getBody();

		Assertions.assertTrue(sessionsBefore.size() < sessionsAfter.size());

		AssignRoomRequest asr = new AssignRoomRequest();
		asr.setAdmin_pk_idx(2);
		csc.assignRoom(newSession.getSession_id(), asr);


		// 상담사가 직접 방을 닫도록 해야한다.
		// 만약 상담사가 아닌 사람이 종료요청을 하면 forbidden 먹는지 여부 테스트
		ResponseEntity<?> closeWithoutAuthorityResponse
				= csc.closeRoom(newSession.getSession_id(), 1);
		System.out.println(closeWithoutAuthorityResponse.getStatusCodeValue()+ " " + HttpStatus.FORBIDDEN.value());
		Assertions.assertTrue(
				closeWithoutAuthorityResponse.getStatusCodeValue()
						== HttpStatus.FORBIDDEN.value());
		ResponseEntity<?> closeWithAuthorityResponse
				= csc.closeRoom(newSession.getSession_id(), 2);
		Assertions.assertTrue(
				closeWithAuthorityResponse.getStatusCodeValue()
						== HttpStatus.OK.value());

		ResponseEntity<?> noSuchRoomExists =
				csc.getMessagesBySessionId("I'm not the valid id! hahaha");
		Assertions.assertTrue(
				noSuchRoomExists.getStatusCodeValue()
				== HttpStatus.NO_CONTENT.value());



	}
}