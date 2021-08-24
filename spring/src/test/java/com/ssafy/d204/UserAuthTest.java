package com.ssafy.d204;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.d204.api.controller.AuthController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAuthTest {
	@Autowired
	AuthController ac;
	@Test
	void doLogin() {
//		ac.register(new UserRegisterPostReq().setEmail("qqqq4@naver.com"));
	}
}
