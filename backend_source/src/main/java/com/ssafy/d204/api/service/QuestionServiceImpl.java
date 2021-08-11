package com.ssafy.d204.api.service;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;
import com.ssafy.d204.db.repository.QuestionDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public ResponseEntity<?> retrieveQuestion(){
        return new ResponseEntity<List<Question>>(questionDao.retrieveQuestion(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> detailQuestion(int pk_idx) {
        return new ResponseEntity<Question>(questionDao.detailQuestion(pk_idx), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> writeQuestion(Question content) {
        boolean result = questionDao.writeQuestion(content);
        if (result) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> updateQuestion(Question content) {

        boolean result = questionDao.updateQuestion(content);
        if (result) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> deleteQuestion(int pk_idx) {
        boolean result = questionDao.deleteQuestion(pk_idx);
        if (result) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> updateNextAnswer(Answer content) {
        boolean result = questionDao.updateNextAnswer(content);
        if (result) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
