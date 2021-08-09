package com.ssafy.d204.db.repository;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionDao {

  List<Question> retrieveQuestion();

  Question detailQuestion(int pk_idx);

  boolean writeQuestion(Question question);

  boolean updateQuestion(Question question);

  boolean deleteQuestion(int pk_idx);

  boolean updateNextAnswer(Answer content);
}
