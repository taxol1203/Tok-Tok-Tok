package com.ssafy.d204.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionCreateReq {
    @ApiModelProperty(example = "세션의 고객 id")
    int fk_client_idx;
    @ApiModelProperty(example = "고객의 pk_idx. fk_created_by_idx와 동일하게 둘 것")
    int fk_created_by_idx;
    @ApiModelProperty(example = "세션을 생성할 때 고객의 qna 목록. 구분자'/'")
    String qna_history;
}
