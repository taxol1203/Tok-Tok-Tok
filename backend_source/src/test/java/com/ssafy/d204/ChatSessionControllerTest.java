package com.ssafy.d204;

import com.mysql.cj.Session;
import com.ssafy.d204.api.controller.QnAController;
import com.ssafy.d204.chat.controller.ChatSessionController;
import com.ssafy.d204.chat.dto.ChatSession;
import com.ssafy.d204.db.entity.Answer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
				(ChatSession) csc.createRoom(createSessionRequest).getBody();





	}
}