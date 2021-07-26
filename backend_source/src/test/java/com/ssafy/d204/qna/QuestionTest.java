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
import com.ssafy.d204.db.entity.Question;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionTest {
	@Autowired
	QnAController qnaController;
	
	ResponseEntity<Question> response;
	ResponseEntity<List<Question>> responses;
	
	static Question q1;
	static Question q2;
	static Question q3;
	@BeforeAll
	static void setUp() {
		q1 = new Question("first");
		q2 = new Question("second");
		q3 = new Question("third");
	}
	
	/*
	Question 테스트
	질문 입력, 삭제, 전체 불러오기 테스트
	*/
	@Test
	void countTest() throws Exception {
		// 전체 질문 가져오기
		responses = qnaController.retrieveQuestion();	
		List<Question> questions = responses.getBody();
		
		// 고정된 1번, 2번 기본키를 제외하고 나머지 질문 삭제
		for(Question qq : questions) {
			if(qq.getPk_idx() == 1 || qq.getPk_idx() == 2)
				continue;
			qnaController.deleteQuestion(qq.getPk_idx());
		}
		
		// 기본 2개를 제외하고 다 지운 뒤, 남은 질문의 개수가 2개인지 확인
		responses = qnaController.retrieveQuestion();	
		questions = responses.getBody();
		Assertions.assertEquals(2,questions.size());	// Defualt 상담 2개는 존재한다.
		
		// 3개 insert
		qnaController.writeQuestion(q1);
		qnaController.writeQuestion(q2);
		qnaController.writeQuestion(q3);
		
		// 기본 2 + 추가 3 == 5인지 확인
		responses = qnaController.retrieveQuestion();	
		questions = responses.getBody();
		Assertions.assertEquals(5,questions.size());
	}
	
	// 질문 수정, 질문 하나 가져오기 테스트
	@Test
	void updateTest() throws Exception {
		responses = qnaController.retrieveQuestion();	
		List<Question> questions = responses.getBody();
		
		for(Question qq : questions) {
			if(qq.getPk_idx() == 1 || qq.getPk_idx() == 2)
				continue;
			qnaController.deleteQuestion(qq.getPk_idx());
		}
		
		// 새롭게 하나의 질문을 추가
		qnaController.writeQuestion(q1);
		
		int curIdx = -1;
		
		responses = qnaController.retrieveQuestion();	
		questions = responses.getBody();
		
		// 기본 질문 2개를 제외하고, 남은 질문 하나의 기본키를 얻는다.
		for(Question qq : questions) {
			if(qq.getPk_idx() != 1 || qq.getPk_idx() != 2) {
				curIdx = qq.getPk_idx();
			}
		}
		// 그 기본 키로 질문 내용 갱신
		Question updateQ = new Question(curIdx, "update");
		qnaController.updateQuestion(updateQ);
		// 제대로 갱신 되었는지 확인
		updateQ = qnaController.detailQuestion(curIdx).getBody();
		Assertions.assertEquals("update", updateQ.getContent());
		
	}
}