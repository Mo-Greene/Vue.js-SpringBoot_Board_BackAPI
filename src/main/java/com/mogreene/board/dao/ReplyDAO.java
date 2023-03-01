package com.mogreene.board.dao;

import com.mogreene.board.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDAO {
    void postReply(ReplyDTO replyDTO);
    List<ReplyDTO> getReplyList(Long BoardNo);
}
