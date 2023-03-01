package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;
    private int total;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;

    public int getSkip() {
        return (page - 1) * 10;
    }

    /* 조회쿼리 */
    private String from;
    private String to;
    private int categoryNo;
    private String keyword;

    public PageDTO(int page, int size, int total) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.endPage = (int)(Math.ceil((double) this.page / 10)) * 10;
        this.startPage = endPage - 9;
        int last = (int)(Math.ceil((total / (double)size)));
        this.endPage = Math.min(endPage, last);
        this.prev = this.startPage > 1;
        this.next = total > this.endPage * this.size;
    }
}
