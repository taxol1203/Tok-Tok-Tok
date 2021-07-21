package com.ssafy.d204.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.repository.AnswerDao;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerDao answerDao;
	
	@Override
	public List<Answer> retrieveAnswer() {
		return answerDao.retrieveAnswer();
	}

	@Override
	public Answer detailAnswer(int pk_idx) {
		return answerDao.detailAnswer(pk_idx);
	}

	@Override
	public boolean writeAnswer(Answer content) {
		return answerDao.writeAnswer(content);
	}

	@Override
	public boolean updateAnswer(Answer content) {
		return answerDao.updateAnswer(content);
	}

	@Override
	public boolean deleteAnswer(int pk_idx) {
		return answerDao.deleteAnswer(pk_idx);
	}

	@Override
	public boolean updateNextQuestion(Answer fk_next_idx) {
		return answerDao.updateNextQuestion(fk_next_idx);
	}

}
