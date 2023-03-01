package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    private Long replyNo;
    private String replyContent;
    private String replyRegDate;
    private Long boardNo;

}
