package com.ssafy.d204.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.d204.api.service.QuestionService;
import com.ssafy.d204.db.entity.Question;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/qna")
public class QnAContoller {
	private static final Logger logger = LoggerFactory.getLogger(QnAContoller.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private QuestionService questionService;

    @ApiOperation(value = "모든 질문의 정보를 반환한다.", response = List.class)
	@GetMapping("/question")
	public ResponseEntity<List<Question>> retrieveQuestion() throws Exception {
		logger.debug("retrieveQuestion - 호출");
		return new ResponseEntity<List<Question>>(questionService.retrieveQuestion(), HttpStatus.OK);
	}

    @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 반환한다.", response = Question.class)    
	@GetMapping("/question/{idx}")
	public ResponseEntity<Question> detailQuestion(@PathVariable int idx) {
		logger.debug("detailQuestion - 호출");
		return new ResponseEntity<Question>(questionService.detailQuestion(idx), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 질문 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/question")
	public ResponseEntity<String> writeQuestion(@RequestBody Question content) {
		logger.debug("writeQuestion - 호출");
		if (questionService.writeQuestion(content)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/question/{idx}")
	public ResponseEntity<String> updateQuestion(@RequestBody Question content) {
		logger.debug("updateQuestion - 호출");
		logger.debug("" + content);
		
		if (questionService.updateQuestion(content)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/question/{idx}")
	public ResponseEntity<String> deleteQuestion(@PathVariable int idx) {
		logger.debug("deleteQuestion - 호출");
		if (questionService.deleteQuestion(idx)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
