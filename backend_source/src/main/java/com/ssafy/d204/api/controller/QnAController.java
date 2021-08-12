package com.ssafy.d204.api.controller;

import com.ssafy.d204.api.response.UserLoginPostRes;
import com.ssafy.d204.api.service.AnswerService;
import com.ssafy.d204.api.service.QuestionService;
import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

// jenkins test

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/qna")
public class QnAController {

    private static final Logger logger = LoggerFactory.getLogger(QnAController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @ApiOperation(value = "모든 질문의 정보를 반환한다.", response = Question.class)
    @GetMapping("/question")
    public ResponseEntity<?> retrieveQuestion() throws Exception {
        logger.debug("retrieveQuestion - 호출");
        try {
          return questionService.retrieveQuestion();
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 반환한다.", response = Question.class)
    @GetMapping("/question/{pk_idx}")
    public ResponseEntity<?> detailQuestion(@PathVariable int pk_idx) {
        logger.debug("detailQuestion - 호출");
        try {
          return questionService.detailQuestion(pk_idx);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "새로운 질문 정보를 입력한다.", response = String.class)
    @PostMapping("/question")
    public ResponseEntity<?> writeQuestion(@RequestBody Question content) {
        logger.debug("writeQuestion - 호출");
        try {
          return questionService.writeQuestion(content);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 수정한다.", response = String.class)
    @PutMapping("/question/{idx}")
    public ResponseEntity<?> updateQuestion(@RequestBody Question content) {
        logger.debug("updateQuestion - 호출");
        logger.debug("" + content);
        try {
          return questionService.updateQuestion(content);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 삭제한다.  ", response = String.class)
    @DeleteMapping("/question/{pk_idx}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int pk_idx) {
        logger.debug("deleteQuestion - 호출");
        try {
          return questionService.deleteQuestion(pk_idx);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // response -> List.class는 아무 의미가 없지 않을까....
    // 대신 실질적으로 의미를 갖는 Answer 클래스를 넣자.
    @ApiOperation(value = "현재 질문에 따른 다음 답변들의 정보를 전부 반환한다.", response = List.class)
    @GetMapping("/question/nextAnswers/{pk_idx}")
    public ResponseEntity<?> getNextAnswers(@PathVariable int pk_idx) throws Exception {
        try {
          return answerService.getNextAnswers(pk_idx);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "현재 질문에 다음 답변을 추가한다. pk_idx에 연결할 답변, fk_previous_idx에 현재 질문의 인덱스를 넣는다.", response = String.class)
    @PutMapping("/question/nextAnswer/{idx}")
    public ResponseEntity<?> updateNextAnswer(@RequestBody Answer content) {
        try {
          return questionService.updateNextAnswer(content);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }

    // ----------------------------- 여기서 부터 답변 -------------------------------

    @ApiOperation(value = "모든 답변의 정보를 반환한다.", response = List.class)
    @GetMapping("/answer")
    public ResponseEntity<?> retrieveAnswer() throws Exception {
        logger.debug("retrieveAnswer - 호출");
        try {
          return answerService.retrieveAnswer();
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "답변 번호에 해당하는 답변의 정보를 반환한다.", response = Answer.class)
    @GetMapping("/answer/{pk_idx}")
    public ResponseEntity<?> detailAnswer(@PathVariable int pk_idx) {
        logger.debug("detailAnswer - 호출");
        try {
          return answerService.detailAnswer(pk_idx);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "답변 번호에 해당하는 다음 질문의 인덱스를 반환한다.", response = Answer.class)
    @GetMapping("/answer/nextQuestion/{pk_idx}")
    public ResponseEntity<?> getNextQuestion(@PathVariable int pk_idx) {
        logger.debug("detailAnswer - 호출");
        try {
          return answerService.getNextQuestion(pk_idx);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiOperation(value = "새로운 답변 정보를 입력한다.", response = String.class)
    /*
    @ApiImplicitParams({  
      @ApiImplicitParam(name = "content", dataType = "varchar", value = "포스트 대상 PK"),
      @ApiImplicitParam(name = "fk_next_idx", dataType = "int4", value = "포스트를 등록한 유저 PK"),
      @ApiImplicitParam(name = "fk_previous_idx", dataType = "int4", value = "포스트를 등록한 유저 PK"),
      })
      */
    @ApiImplicitParam(name = "content", dataType = "varchar", value = "content, fk_next_idx를 입력하여 전송 할 수 있다. \\\"fk_next_idx\\\": 0 를 지우고 execute를 하면 연결되는 다음 질문의 기본 값(fk_next_idx)는 2(상담종료)이다. (0으로 실행하면 오류)\"")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "로그인 성공, 토큰과 유저정보 반환",response = UserLoginPostRes.class),
        @ApiResponse(code = 401, message = "아이디 혹은 비밀번호 일치하지 않음", response = Void.class)
    })
    @PostMapping("/answer")
    public ResponseEntity<?> writeAnswer(@RequestBody Answer content) {
        logger.debug("writeAnswer - 호출");
        try {
          return answerService.writeAnswer(content);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "답변 번호에 해당하는 답변의 정보를 수정한다 . (content, fk_next_idx) 여기서 이전 질문(fk_previous_idx)는 변경하지 않는다.", response = String.class)
    @PutMapping("/answer/{idx}")
    public ResponseEntity<?> updateAnswer(@RequestBody Answer content) {
        logger.debug("updateAnswer - 호출");
        logger.debug("" + content);
        try {
          return answerService.updateAnswer(content);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "답변 번호에 해당하는 질문의 정보를 삭제한다.", response = String.class)
    @DeleteMapping("/answer/{pk_idx}")
    public ResponseEntity<?> deleteAnswer(@PathVariable int pk_idx) {
        logger.debug("deleteAnswer - 호출");
        try {
          return answerService.deleteAnswer(pk_idx);
        }catch (Exception e) {
          return new ResponseEntity<String>("Exception: " + e.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "답변 번호에 해당하는 다음 질문의 번호를 수정한다. 다음 질문의 기본 값 중 1번은 (상담사 연결) 2번은 (상담 종료)이다.", response = String.class)
    @PutMapping("/answer/nextIdx/{pk_idx}")
    public ResponseEntity<?> updateNextQuestion(@RequestBody Answer fk_next_idx) {
      try {
        return answerService.updateNextQuestion(fk_next_idx);
      }catch (Exception e) {
        return new ResponseEntity<String>("Exception: " + e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}
