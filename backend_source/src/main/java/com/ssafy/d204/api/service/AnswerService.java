package com.ssafy.d204.api.service;

import java.util.List;

import com.ssafy.d204.db.entity.Answer;

public interface AnswerService {
	public List<Answer> retrieveAnswer();
	public Answer detailAnswer(int pk_idx);
	public boolean writeAnswer(Answer content);
	public boolean updateAnswer(Answer content);
	public boolean deleteAnswer(int pk_idx);
	public boolean updateNextQuestion(Answer fk_next_idx);
	public int getNextQuestion(int pk_idx);
	public List<Answer> getNextAnswers(int pk_idx);
}
