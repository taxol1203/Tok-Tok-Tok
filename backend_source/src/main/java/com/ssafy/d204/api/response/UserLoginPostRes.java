package com.ssafy.d204.api.response;

import com.ssafy.d204.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginPostRes {
    @ApiModelProperty(example = "jwt-token here!")
    String token;
    User user;
}
