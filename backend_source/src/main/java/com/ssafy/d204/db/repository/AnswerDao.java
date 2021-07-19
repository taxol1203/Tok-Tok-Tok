package com.ssafy.d204.db.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.d204.db.entity.Answer;

@Mapper
public interface AnswerDao {
	public List<Answer> retrieveAnswer();
	public Answer detailAnswer(int pk_idx);
	public boolean writeAnswer(Answer answer);
	public boolean updateAnswer(Answer answer);
	public boolean deleteAnswer(int pk_idx);
	public boolean updateNextQuestion(Answer fk_next_idx);
}
