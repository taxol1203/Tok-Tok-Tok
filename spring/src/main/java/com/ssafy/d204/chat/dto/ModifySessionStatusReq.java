package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ModifySessionStatusReq {
    @ApiModelProperty(example = "해당 세션의 변경하고 싶은 상태를 기술")
    String status;
}
