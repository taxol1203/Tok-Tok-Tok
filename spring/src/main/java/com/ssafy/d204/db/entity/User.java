package com.ssafy.d204.db.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class User {
    @ApiModelProperty(example = "4")
    long pk_idx;
    @ApiModelProperty(example = "sample@gmail.com")
    String email;
    @ApiModelProperty(example = "!this field returns with empty string due to Security!")
    String passwd;
    @ApiModelProperty(example = "SampleMan")
    String username;
}
