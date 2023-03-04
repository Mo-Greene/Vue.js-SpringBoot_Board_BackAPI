package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long boardNo;

    @NotBlank
    @Size(min = 4, max = 100)
    private String boardTitle;

    @NotBlank
    @Size(min = 4, max = 2000)
    private String boardContent;

    @NotBlank
    @Size(min = 3, max = 5)
    private String boardWriter;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z1-9]{4,16}")
    private String boardPassword;
    private String boardRegDate;
    private String boardModDate;
    private int boardView;
    private int categoryNo;
    private String categoryContent;
    private Long fileNo;
    private List<ReplyDTO> replyList;

}
