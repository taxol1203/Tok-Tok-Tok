package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatMessage {
    @ApiModelProperty(example = "메세지의 id(빈칸)")
    private int pk_idx;
    @ApiModelProperty(example = "메세지 내용")
    private String message;
    @ApiModelProperty(example = "메세지를 작성한 회원의 int pk_idx")
    private int fk_author_idx;
    @ApiModelProperty(example = "메세지 전송날짜(빈칸)")
    private String created; // 생성날짜.
    @ApiModelProperty(example = "메세지가 삭제되었는지 여부(빈칸)")
    private boolean deleted = false; // 삭제되었는지 여부. 만약 true일 경우 유저에게 숨기기
    @ApiModelProperty(example = "해당 메세지의 대상 세션 id")
    private String fk_session_id;
    @ApiModelProperty(example = "메세지의 type.")
    private String type;
//    public enum MessageType{
//        JOIN, QUIT, MSG, VID, NEWR, DELR;
//    }
}

