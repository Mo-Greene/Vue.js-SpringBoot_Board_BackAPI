package com.mogreene.board.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

/**
 * 페이지 response
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {

    /* 페이지 */
    @Builder.Default
    @Positive
    private int page = 1;

    /* 한 페이지 당 게시글 */
    @Builder.Default
    @Positive
    private int size = 10;

    /* 게시글 총 개수 */
    private int total;

    /* 게시글 블록 시작 페이지 */
    private int startPage;

    /* 게시글 블록 끝 페이지 */
    private int endPage;

    /* 게시글 블록 이전 유무 */
    private boolean prev;

    /* 게시글 블록 다음 유무 */
    private boolean next;

    /* 게시글 스킵 (Mapper 에서 limit 으로 사용) */
    public int getSkip() {
        return (page - 1) * 10;
    }

    /* 조회쿼리 - 시작날짜 */
    private String from;

    /* 조회쿼리 - 끝날짜 */
    private String to;

    /* 조회쿼리 - 카테고리 */
    private int categoryNo;

    /* 조회쿼리 - 검색징 */
    private String keyword;
}
