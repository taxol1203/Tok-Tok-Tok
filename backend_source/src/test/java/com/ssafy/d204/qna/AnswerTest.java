package com.ssafy.d204.qna;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.ssafy.d204.api.controller.QnAController;
import com.ssafy.d204.db.entity.Answer;

@SpringBootTest
@AutoConfigureMockMvc
public class AnswerTest {
	@Autowired
	QnAController qnaController;
	
	ResponseEntity<Answer> response;
	ResponseEntity<List<Answer>> responses;
	
	static Answer a1;
	static Answer a2;
	static Answer a3;
	@BeforeAll
	static void setUp() {
		a1 = new Answer("first");
		a2 = new Answer("second");
		a3 = new Answer("third");
	}
	
	/*
		Answer 테스트
		답변 입력, 삭제, 전체 불러오기 테스트
	*/
	@Test
	void countTest() throws Exception {
		// 전체 답변 불러오기
		responses = qnaController.retrieveAnswer();	
		List<Answer> answers = responses.getBody();
		
		// 전체 답변 지움
		for(Answer aa : answers) {
			qnaController.deleteAnswer(aa.getPk_idx());
		}
		
		// 답변의 개수가 0개인지 확인
		responses = qnaController.retrieveAnswer();	
		answers = responses.getBody();
		Assertions.assertEquals(0,answers.size());
		
		// 3개의 답변 insert
		qnaController.writeAnswer(a1);
		qnaController.writeAnswer(a2);
		qnaController.writeAnswer(a3);
		
		// 총 3개의 답변인지 확인
		responses = qnaController.retrieveAnswer();	
		answers = responses.getBody();
		Assertions.assertEquals(3,answers.size());
	}
	
	// 답변 수정, 답변 하나 가져오기 테스트
	@Test
	void updateTest() throws Exception {
		// 전체 답변 불러오기
		responses = qnaController.retrieveAnswer();	
		List<Answer> answers = responses.getBody();
		
		// 전체 답변 지우기
		for(Answer aa : answers) {
			qnaController.deleteAnswer(aa.getPk_idx());
		}
		
		// 하나의 답변 insert
		qnaController.writeAnswer(a1);
		
		// 하나의 답변의 기본키를 얻는다.
		int curIdx;
		responses = qnaController.retrieveAnswer();	
		answers = responses.getBody();
		curIdx = answers.get(0).getPk_idx();
		
		// 얻은 기본키를 통하여 답변 수정
		Answer updateQ = new Answer(curIdx, "updated");
		qnaController.updateAnswer(updateQ);
		
		// 제대로 수정 되었는지 확인
		updateQ = qnaController.detailAnswer(curIdx).getBody();
		Assertions.assertEquals("updated", updateQ.getContent());
	}
	
	// 답변에 연계된 질문 수정하기
	@Test
	void updateNextQuestion() throws Exception {
		// 전체 답변 불러오고 샂게
		responses = qnaController.retrieveAnswer();	
		List<Answer> answers = responses.getBody();
		
		for(Answer aa : answers) {
			qnaController.deleteAnswer(aa.getPk_idx());
		}
		// 하나의 답변 넣고, 그 기본키를 얻는다.
		qnaController.writeAnswer(a1);
		int curIdx;
		responses = qnaController.retrieveAnswer();	
		answers = responses.getBody();
		
		curIdx = answers.get(0).getPk_idx();
		
		// 얻은 기본키를 통하여 연계된 질문 외래키를 변경한다.
		Answer updateQ = new Answer(curIdx, 1);
		qnaController.updateNextQuestion(updateQ);
		// 제대로 변경 되었는지 확인
		updateQ = qnaController.detailAnswer(curIdx).getBody();
		Assertions.assertEquals(1, updateQ.getFk_next_idx());
		Assertions.assertEquals(1, qnaController.getNextQuestion(curIdx).getBody());
	}
}