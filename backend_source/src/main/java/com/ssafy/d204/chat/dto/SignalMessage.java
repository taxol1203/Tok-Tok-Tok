package com.ssafy.d204.chat.dto;

import lombok.Data;

@Data
public class SignalMessage {

  private String type;
  private String dest;
  private Object data;
}
