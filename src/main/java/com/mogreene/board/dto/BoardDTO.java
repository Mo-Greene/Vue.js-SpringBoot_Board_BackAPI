package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private String boardPassword;
    private String boardRegDate;
    private String boardModDate;
    private int boardView;
    private int categoryNo;
    private String categoryContent;

}
