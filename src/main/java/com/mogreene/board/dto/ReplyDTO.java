package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 댓글 DTO
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

    /**
     * 댓글 pk
     */
    private Long replyNo;

    /**
     * 댓글 내용
     */
    private String replyContent;

    /**
     * 댓글 생성일
     */
    private String replyRegDate;

    /**
     * 게시글 fk
     */
    private Long boardNo;

}
