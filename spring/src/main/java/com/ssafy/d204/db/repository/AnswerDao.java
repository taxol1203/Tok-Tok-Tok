package com.ssafy.d204.db.repository;

import com.ssafy.d204.db.entity.Answer;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnswerDao {

    List<Answer> retrieveAnswer();

    Answer detailAnswer(int pk_idx);

    boolean writeAnswer(Answer answer);

    boolean updateAnswer(Answer answer);

    boolean deleteAnswer(int pk_idx);

    boolean updateNextQuestion(Answer fk_next_idx);

    int getNextQuestion(int pk_idx);

    List<Answer> getNextAnswers(int pk_idx);
}
