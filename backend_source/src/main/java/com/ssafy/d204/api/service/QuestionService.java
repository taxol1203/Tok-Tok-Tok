package com.ssafy.d204.api.service;

import java.util.List;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;

public interface QuestionService {
	public List<Question> retrieveQuestion();
	public Question detailQuestion(int pk_idx);
	public boolean writeQuestion(Question content);
	public boolean updateQuestion(Question content);
	public boolean deleteQuestion(int pk_idx);
	public boolean updateNextAnswer(Answer content);
}
