package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import lombok.Data;

@Data
public class ChatSession {
    @ApiModelProperty(example = "세션의 고유id, String")
    private String session_id;
    @ApiModelProperty(example = "세션 생성날짜")
    private String created_at; // long으로 바꿀 수 있는지 생각해보기
    @ApiModelProperty(example = "세션 생성자의 pk_idx")
    private int fk_created_by_idx;
    @ApiModelProperty(example = "고객의 pk_idx")
    private int fk_client_idx;
    @ApiModelProperty(example = "상담사의 pk_idx")
    private int fk_host_idx;
    @ApiModelProperty(example = "세션의 접근허가 id(미구현)")
    private int fk_permission_idx;
    @ApiModelProperty(example = "세션을 생성할 때의 qna 목록")
    private String qna_history;
    @ApiModelProperty(example = "세션 생성할 때 선택했던 qna 목록")
    String status;
    @ApiModelProperty(example = "읽지 않은 메세지 갯수 (미구현)")
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
