package com.mogreene.board.dto.page;

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
public class PageRequestDTO {

    @Builder.Default
    @Positive
    private int page = 1;

    @Builder.Default
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
}
