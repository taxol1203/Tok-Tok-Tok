package com.ssafy.d204.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseDaoSupport extends SqlSessionDaoSupport {

  @Autowired(required = false)
  @Override
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    super.setSqlSessionFactory(sqlSessionFactory);
  }

  @Autowired(required = false)
  @Override
  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    super.setSqlSessionTemplate(sqlSessionTemplate);
  }
}
