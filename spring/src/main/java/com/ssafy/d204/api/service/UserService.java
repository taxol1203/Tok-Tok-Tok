package com.ssafy.d204.api.service;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserLoginPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.db.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> loginUser(UserLoginPostReq loginInfo);

    ResponseEntity<?> registUser(UserRegisterPostReq info);

    ResponseEntity<?> checkEmail(DuplicateCheckEmailPostReq email);

    ResponseEntity<?> getUserByIdx(int pk_idx);
}
