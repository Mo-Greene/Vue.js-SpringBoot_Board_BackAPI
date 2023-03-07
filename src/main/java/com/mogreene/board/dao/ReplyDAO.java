package com.mogreene.board.dao;

import com.mogreene.board.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDAO {
    /**
     * 댓글 등록
     * @param replyDTO
     */
    void postReply(ReplyDTO replyDTO);

    /**
     * 댓글 리스트
     * @param BoardNo
     * @return
     */
    List<ReplyDTO> getReplyList(Long BoardNo);
}
