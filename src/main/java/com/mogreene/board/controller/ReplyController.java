package com.mogreene.board.controller;

import com.mogreene.board.dto.ReplyDTO;
import com.mogreene.board.dto.api.ApiResponseDTO;
import com.mogreene.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 등록
     * @param boardNo
     * @param replyDTO
     * @return
     */
    @PostMapping("/notice/{boardNo}/reply")
    public ApiResponseDTO<?> postReply(@PathVariable("boardNo") Long boardNo,
                                            @RequestBody ReplyDTO replyDTO) {

        replyDTO.setBoardNo(boardNo);

        replyService.postReply(replyDTO);

        return ApiResponseDTO.builder()
                .resultType(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultData("Success")
                .build();
    }
}
