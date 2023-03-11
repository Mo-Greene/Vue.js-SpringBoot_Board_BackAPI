package com.mogreene.board.dto.page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 페이지 request
 * @author mogreene
 */
@Getter
@ToString
public class PageResponseDTO {

    /* 게시글 페이징 */
    private int page;

    /* 한 페이지 당 게시글 */
    private int size;

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

    /**
     * 게시글 페이지 객체로 리턴
     * @param pageRequestDTO
     * @param total
     */
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, int total) {
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;

        this.endPage = (int)(Math.ceil((double) this.page / 10)) * 10;
        this.startPage = endPage - 9;

        /* 총 게시물을 고려한 마지막 페이지 */
        int last = (int)(Math.ceil((total / (double)size)));

        this.endPage = Math.min(endPage, last);

        this.prev = this.startPage > 1;
        this.next = total > this.endPage * this.size;
    }
}
