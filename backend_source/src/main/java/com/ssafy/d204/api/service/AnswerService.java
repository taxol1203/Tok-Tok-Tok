package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import java.util.List;

public interface AnswerService {

  List<Answer> retrieveAnswer();

  Answer detailAnswer(int pk_idx);

  boolean writeAnswer(Answer content);

  boolean updateAnswer(Answer content);

  boolean deleteAnswer(int pk_idx);

  boolean updateNextQuestion(Answer fk_next_idx);

  int getNextQuestion(int pk_idx);

  List<Answer> getNextAnswers(int pk_idx);
}
