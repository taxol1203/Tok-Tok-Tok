package com.ssafy.d204.api.controller;

import com.ssafy.d204.api.service.AnswerService;
import com.ssafy.d204.api.service.QuestionService;
import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import io.swagger.annotations.ApiOperation;
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

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/qna")
public class QnAController {

  private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  @Autowired
  private QuestionService questionService;

  @Autowired
  private AnswerService answerService;

  @ApiOperation(value = "모든 질문의 정보를 반환한다.", response = List.class)
  @GetMapping("/question")
  public ResponseEntity<List<Question>> retrieveQuestion() throws Exception {
    logger.debug("retrieveQuestion - 호출");
    return new ResponseEntity<List<Question>>(questionService.retrieveQuestion(), HttpStatus.OK);
  }

  @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 반환한다.", response = Question.class)
  @GetMapping("/question/{pk_idx}")
  public ResponseEntity<Question> detailQuestion(@PathVariable int pk_idx) {
    logger.debug("detailQuestion - 호출");
    return new ResponseEntity<Question>(questionService.detailQuestion(pk_idx), HttpStatus.OK);
  }

  @ApiOperation(value = "새로운 질문 정보를 입력한다.", response = String.class)
  @PostMapping("/question")
  public ResponseEntity<String> writeQuestion(@RequestBody Question content) {
    logger.debug("writeQuestion - 호출");
    if (questionService.writeQuestion(content)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 수정한다.", response = String.class)
  @PutMapping("/question/{idx}")
  public ResponseEntity<String> updateQuestion(@RequestBody Question content) {
    logger.debug("updateQuestion - 호출");
    logger.debug("" + content);

    if (questionService.updateQuestion(content)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "질문 번호에 해당하는 질문의 정보를 삭제한다.  ", response = String.class)
  @DeleteMapping("/question/{pk_idx}")
  public ResponseEntity<String> deleteQuestion(@PathVariable int pk_idx) {
    logger.debug("deleteQuestion - 호출");
    if (questionService.deleteQuestion(pk_idx)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "현재 질문에 따른 다음 답변들의 정보를 전부 반환한다.", response = List.class)
  @GetMapping("/question/nextAnswers/{pk_idx}")
  public ResponseEntity<List<Answer>> getNextAnswers(@PathVariable int pk_idx) throws Exception {
    return new ResponseEntity<List<Answer>>(answerService.getNextAnswers(pk_idx), HttpStatus.OK);
  }

  @ApiOperation(value = "현재 질문에 다음 답변을 추가한다. pk_idx에 연결할 답변, fk_previous_idx에 현재 질문의 인덱스를 넣는다.", response = String.class)
  @PutMapping("/question/nextAnswer/{idx}")
  public ResponseEntity<String> updateNextAnswer(@RequestBody Answer content) {
    if (questionService.updateNextAnswer(content)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  // ----------------------------- 여기서 부터 답변 -------------------------------

  @ApiOperation(value = "모든 답변의 정보를 반환한다.", response = List.class)
  @GetMapping("/answer")
  public ResponseEntity<List<Answer>> retrieveAnswer() throws Exception {
    logger.debug("retrieveAnswer - 호출");
    return new ResponseEntity<List<Answer>>(answerService.retrieveAnswer(), HttpStatus.OK);
  }

  @ApiOperation(value = "답변 번호에 해당하는 답변의 정보를 반환한다.", response = Answer.class)
  @GetMapping("/answer/{pk_idx}")
  public ResponseEntity<Answer> detailAnswer(@PathVariable int pk_idx) {
    logger.debug("detailAnswer - 호출");
    return new ResponseEntity<Answer>(answerService.detailAnswer(pk_idx), HttpStatus.OK);
  }

  @ApiOperation(value = "답변 번호에 해당하는 다음 질문의 인덱스를 반환한다.", response = Answer.class)
  @GetMapping("/answer/nextQuestion/{pk_idx}")
  public ResponseEntity<Integer> getNextQuestion(@PathVariable int pk_idx) {
    logger.debug("detailAnswer - 호출");
    return new ResponseEntity<Integer>(answerService.getNextQuestion(pk_idx), HttpStatus.OK);
  }

  @ApiOperation(value = "새로운 답변 정보를 입력한다. title, content, fk_next_idx를 입력하여 전송 할 수 있다. \"fk_next_idx\": 0 를 지우고 execute를 하면 연결되는 다음 질문의 기본 값(fk_next_idx)는 2(상담종료)이다. (0으로 실행하면 오류)", response = String.class)
  @PostMapping("/answer")
  public ResponseEntity<String> writeAnswer(@RequestBody Answer content) {
    logger.debug("writeAnswer - 호출");
    if (answerService.writeAnswer(content)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "답변 번호에 해당하는 답변의 정보를 수정한다. (content, fk_next_idx) 여기서 이전 질문(fk_previous_idx)는 변경하지 않는다.", response = String.class)
  @PutMapping("/answer/{idx}")
  public ResponseEntity<String> updateAnswer(@RequestBody Answer content) {
    logger.debug("updateAnswer - 호출");
    logger.debug("" + content);

    if (answerService.updateAnswer(content)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "답변 번호에 해당하는 질문의 정보를 삭제한다.", response = String.class)
  @DeleteMapping("/answer/{pk_idx}")
  public ResponseEntity<String> deleteAnswer(@PathVariable int pk_idx) {
    logger.debug("deleteAnswer - 호출");
    if (answerService.deleteAnswer(pk_idx)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }

  @ApiOperation(value = "답변 번호에 해당하는 다음 질문의 번호를 수정한다. 다음 질문의 기본 값 중 1번은 (상담사 연결) 2번은 (상담 종료)이다.", response = String.class)
  @PutMapping("/answer/nextIdx/{pk_idx}")
  public ResponseEntity<String> updateNextQuestion(@RequestBody Answer fk_next_idx) {
    if (answerService.updateNextQuestion(fk_next_idx)) {
      return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
  }
}
