package com.mogreene.board.service;

import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyDAO replyDAO;

    public void postReply(ReplyDTO replyDTO) {
        replyDAO.postReply(replyDTO);
    }
}
