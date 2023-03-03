package com.mogreene.board.dto.page;

import com.mogreene.board.dto.BoardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Pagination {

    private int page;
    private int size;
    private int total;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private List<BoardDTO> dtoList;

    @Builder(builderMethodName = "withAll")
    public Pagination(PageRequestDTO pageRequestDTO, List<BoardDTO> dtoList, int total) {
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.dtoList = dtoList;

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
