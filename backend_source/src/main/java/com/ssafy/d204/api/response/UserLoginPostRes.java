package com.ssafy.d204.api.response;

import com.ssafy.d204.db.entity.User;
import lombok.Data;

@Data
public class UserLoginPostRes {
	String token;
	User user;
}
