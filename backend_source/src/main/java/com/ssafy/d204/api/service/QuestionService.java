package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import java.util.List;

public interface QuestionService {

  List<Question> retrieveQuestion();

  Question detailQuestion(int pk_idx);

  boolean writeQuestion(Question content);

  boolean updateQuestion(Question content);

  boolean deleteQuestion(int pk_idx);

  boolean updateNextAnswer(Answer content);
}
