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

    /* 댓글 pk */
    private Long replyNo;

    /* 댓글 내용 */
    private String replyContent;

    /* 댓글 생성 날짜 */
    private String replyRegDate;

    /* 게시글 번호 fk */
    private Long boardNo;

}
