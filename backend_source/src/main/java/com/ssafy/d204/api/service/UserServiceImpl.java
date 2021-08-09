package com.ssafy.d204.api.service;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.db.entity.User;
import com.ssafy.d204.db.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUserByIdx(int pk_idx) {
        return userDao.getUserByIdx(pk_idx);
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUser(id);
    }

    @Override
    public int registUser(UserRegisterPostReq info) {
        return userDao.registUser(info);
    }

    @Override
    public User checkEmail(DuplicateCheckEmailPostReq email) {
        return userDao.checkEmail(email);
    }


}
