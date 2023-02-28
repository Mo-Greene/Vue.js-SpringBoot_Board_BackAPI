package com.mogreene.board.dao;

import com.mogreene.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDAO {
    void postArticle(BoardDTO boardDTO);
    BoardDTO getArticleView(Long boardNo);
    void viewCount(Long boardNo);
    void deleteArticle(Long boardNo);
    void modifyArticle(BoardDTO boardDTO);
}