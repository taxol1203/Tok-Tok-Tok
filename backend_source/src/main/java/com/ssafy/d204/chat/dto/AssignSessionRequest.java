package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignSessionRequest {
    @ApiModelProperty(example = "상담사의 int pk_idx")
    int admin_pk_idx;
}
