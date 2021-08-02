package com.ssafy.d204.api.service;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.db.entity.User;

public interface UserService {
	User getUserById(String id);
	int registUser(UserRegisterPostReq info);
	User checkEmail(DuplicateCheckEmailPostReq email);
	public User getUserByIdx(int pk_idx);
}
