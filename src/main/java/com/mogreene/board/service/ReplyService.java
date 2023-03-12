package com.mogreene.board.service;

import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 댓글 Service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyDAO replyDAO;
    private final BoardDAO boardDAO;

    /**
     * 댓글 등록
     * @param replyDTO
     */
    public void postReply(ReplyDTO replyDTO) {

        if (boardDAO.findByBoardNo(replyDTO.getBoardNo()) == null) {
            throw new RuntimeException("맞지 않는 게시글 입니다.");
        }

        replyDAO.postReply(replyDTO);
    }
}
