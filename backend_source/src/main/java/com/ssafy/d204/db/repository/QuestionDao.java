package com.ssafy.d204.db.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.d204.db.entity.Answer;
import com.ssafy.d204.db.entity.Question;

@Mapper
public interface QuestionDao {
	public List<Question> retrieveQuestion();
	public Question detailQuestion(int pk_idx);
	public boolean writeQuestion(Question question);
	public boolean updateQuestion(Question question);
	public boolean deleteQuestion(int pk_idx);
	public boolean updateNextAnswer(Answer content);
}
