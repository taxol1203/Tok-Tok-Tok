package com.ssafy.d204.api.request;

/*
 * POST /login
 *
 * */

import lombok.Data;


@Data
public class UserLoginPostReq {

  String email;
  String passwd;
}
	