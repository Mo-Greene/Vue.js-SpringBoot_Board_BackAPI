package com.mogreene.board.controller;

import com.mogreene.board.common.api.ApiResponseDTO;
import com.mogreene.board.dto.ReplyDTO;
import com.mogreene.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 컨트롤러
 * @author mogreene
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 등록
     * @param boardNo
     * @param replyDTO
     * @return
     */
    @PostMapping("/boards/notice/reply/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> postReply(@PathVariable("boardNo") Long boardNo,
                                                      @RequestBody ReplyDTO replyDTO) {

        replyDTO.setBoardNo(boardNo);

        replyService.postReply(replyDTO);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.NO_CONTENT)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultData("Success")
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
    }
}
