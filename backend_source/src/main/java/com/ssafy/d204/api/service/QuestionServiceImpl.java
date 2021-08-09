package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import com.ssafy.d204.db.repository.QuestionDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  @Autowired
  private QuestionDao questionDao;

  @Override
  public List<Question> retrieveQuestion() {
    return questionDao.retrieveQuestion();
  }

  @Override
  public Question detailQuestion(int pk_idx) {
    return questionDao.detailQuestion(pk_idx);
  }

  @Override
  public boolean writeQuestion(Question content) {
    return questionDao.writeQuestion(content);
  }

  @Override
  public boolean updateQuestion(Question content) {
    return questionDao.updateQuestion(content);
  }

  @Override
  public boolean deleteQuestion(int pk_idx) {
    return questionDao.deleteQuestion(pk_idx);
  }

  @Override
  public boolean updateNextAnswer(Answer content) {
    return questionDao.updateNextAnswer(content);
  }

}
