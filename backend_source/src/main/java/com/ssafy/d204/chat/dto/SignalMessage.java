package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignalMessage {
    private String type;
    private String dest;
    private Object data;
}
