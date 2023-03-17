package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 게시글 DTO
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    /* 게시글 pk */
    private Long boardNo;

    /* 게시글 제목 */
    @NotBlank
    @Size(min = 4, max = 100)
    private String boardTitle;

    /* 게시글 내용 */
    @NotBlank
    @Size(min = 4, max = 2000)
    private String boardContent;

    /* 게시글 작성자 */
    @NotBlank
    @Size(min = 3, max = 5)
    private String boardWriter;

    /* 게시글 비밀번호 */
    @NotBlank
    @Pattern(regexp = "[a-zA-Z1-9]{4,16}")
    private String boardPassword;

    /* 게시글 비밀번호 확인 */
    private String boardPasswordCheck;

    /* 게시글 생성일자 */
    private String boardRegDate;

    /* 게시글 수정일자 */
    private String boardModDate;

    /* 게시글 조회수 */
    private int boardView;

    /* 카테고리 fk */
    private int categoryNo;

    /* 카테고리 내용 */
    private String categoryContent;

    /* 파일 존재 유무 */
    private boolean isExistFile = false;

    /* 게시글 내 댓글 리스트 */
    private List<ReplyDTO> replyList;
}
