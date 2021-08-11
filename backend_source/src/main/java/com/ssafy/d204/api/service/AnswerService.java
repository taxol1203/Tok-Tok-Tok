package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface AnswerService {

    ResponseEntity<?> retrieveAnswer();

    ResponseEntity<?> detailAnswer(int pk_idx);

    ResponseEntity<?> writeAnswer(Answer content);

    ResponseEntity<?> updateAnswer(Answer content);

    ResponseEntity<?> deleteAnswer(int pk_idx);

    ResponseEntity<?> updateNextQuestion(Answer fk_next_idx);

    ResponseEntity<?> getNextQuestion(int pk_idx);

    ResponseEntity<?> getNextAnswers(int pk_idx);
}
