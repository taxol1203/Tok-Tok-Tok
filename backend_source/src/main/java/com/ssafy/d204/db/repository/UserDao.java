package com.ssafy.d204.db.repository;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.db.entity.User;

public interface UserDao {
	User getUser(String id);
	int registUser(UserRegisterPostReq info);
	User checkEmail(DuplicateCheckEmailPostReq email);
	User getUserByIdx(int pk_idx);
}
