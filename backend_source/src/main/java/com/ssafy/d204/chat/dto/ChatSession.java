package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.Data;

@Data
public class ChatSession {
    @ApiModelProperty(example = "3ff711a0-23b3-428a-96fa-thisissample")
    private String session_id;
    @ApiModelProperty(example = "2021-08-09 14:49:05.465979+09")
    private String created_at; // long으로 바꿀 수 있는지 생각해보기
    @ApiModelProperty(example = "7", value="1234")
    private int fk_created_by_idx;
    @ApiModelProperty(example = "7")
    private int fk_client_idx;
    @ApiModelProperty(example = "3")
    private int fk_host_idx;
    @ApiModelProperty(example = "-1", value="미구현")
    private int fk_permission_idx;
    @ApiModelProperty(example = "/")
    private String qna_history;
    @ApiModelProperty(example = "OPEN")
    String status;
    @ApiModelProperty(example = "7", value="미구현")
    int unread;

    //    SessionState status;
//    public enum SessionState{
//        OPEN, // 막 상담이 열린 상태
//        END,  // 상담 종료
//        LIVE // 상담이 진행중임. 전담 상담사 배정된 상태
//    }
    public static ChatSession create() {
        ChatSession chatSession = new ChatSession();
        chatSession.session_id = UUID.randomUUID().toString();

        return chatSession;
    }
}
