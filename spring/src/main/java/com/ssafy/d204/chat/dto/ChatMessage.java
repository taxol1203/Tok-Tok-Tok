package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatMessage {
    @ApiModelProperty(example = "1")
    private int pk_idx;
    @ApiModelProperty(example = "Hello, Everyone! I'm the best in the Universe!")
    private String message;
    @ApiModelProperty(example = "3")
    private int fk_author_idx;
    @ApiModelProperty(example = "2021-08-09 14:49:05.465979+09")
    private String created; // 생성날짜.
    @ApiModelProperty(example = "false")
    private boolean deleted = false; // 삭제되었는지 여부. 만약 true일 경우 유저에게 숨기기
    @ApiModelProperty(example = "1a2a3a4a5a-6a1234-a123-412-41124124")
    private String fk_session_id;
    @ApiModelProperty(example = "MSG")
    private String type;
//    public enum MessageType{
//        JOIN, QUIT, MSG, VID, NEWR, DELR;
//    }
}

