package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

    ResponseEntity<?> retrieveQuestion();

    ResponseEntity<?> detailQuestion(int pk_idx);

    ResponseEntity<?> writeQuestion(Question content);

    ResponseEntity<?> updateQuestion(Question content);

    ResponseEntity<?> deleteQuestion(int pk_idx);

    ResponseEntity<?> updateNextAnswer(Answer content);
}
