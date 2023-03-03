package com.mogreene.board.service;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.ReplyDTO;
import com.mogreene.board.util.ServiceModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyDAO replyDAO;
    private final ServiceModule serviceModule;

    /**
     * 댓글 등록
     * @param replyDTO
     */
    public void postReply(ReplyDTO replyDTO) throws CustomException {

        serviceModule.validBoardNo(replyDTO.getBoardNo());

        replyDAO.postReply(replyDTO);
    }
}
