package com.mogreene.board.controller;

import com.mogreene.board.dto.ReplyDTO;
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

    @PostMapping("/{boardNo}/reply")
    public ResponseEntity<String> postReply(@PathVariable("boardNo") Long boardNo,
                                            @RequestBody ReplyDTO replyDTO) {

        replyDTO.setBoardNo(boardNo);

        replyService.postReply(replyDTO);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
