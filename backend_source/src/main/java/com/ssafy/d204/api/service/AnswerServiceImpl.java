package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.repository.AnswerDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Override
    public ResponseEntity<?> retrieveAnswer() {
        return new ResponseEntity<List<Answer>>(answerDao.retrieveAnswer(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> detailAnswer(int pk_idx) {
        return new ResponseEntity<Answer>(answerDao.detailAnswer(pk_idx), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> writeAnswer(Answer content) {
      if (answerDao.writeAnswer(content)) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> updateAnswer(Answer content) {
      if (answerDao.updateAnswer(content)) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> deleteAnswer(int pk_idx) {
      if (answerDao.deleteAnswer(pk_idx)) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> updateNextQuestion(Answer fk_next_idx) {
      if (answerDao.updateNextQuestion(fk_next_idx)) {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getNextQuestion(int pk_idx) {
        return new ResponseEntity<Integer>(answerDao.getNextQuestion(pk_idx), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getNextAnswers(int pk_idx) {
      return new ResponseEntity<List<Answer>>(answerDao.getNextAnswers(pk_idx), HttpStatus.OK);
    }

}
