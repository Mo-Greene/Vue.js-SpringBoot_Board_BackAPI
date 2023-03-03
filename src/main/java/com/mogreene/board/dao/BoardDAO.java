package com.mogreene.board.dao;

import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDAO {
    List<BoardDTO> getArticleList(PageDTO pageDTO);
    int totalCount(PageDTO pageDTO);
    void postArticle(BoardDTO boardDTO);
    BoardDTO getArticleView(Long boardNo);
    void viewCount(Long boardNo);
    void deleteArticle(Long boardNo);
    void modifyArticle(BoardDTO boardDTO);
    String dbPassword(BoardDTO boardDTO);
    Boolean findByBoardNo(Long boardNo);
}
