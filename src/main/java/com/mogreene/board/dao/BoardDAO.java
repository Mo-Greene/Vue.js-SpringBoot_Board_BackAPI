package com.mogreene.board.dao;

import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 게시글 DAO
 * @author mogreene
 */
@Mapper
public interface BoardDAO {
    /**
     * 게시글 리스트
     * @param pageRequestDTO
     * @return
     */
    List<BoardDTO> getArticleList(PageRequestDTO pageRequestDTO);

    /**
     * 게시글 총 개수
     * @param pageRequestDTO
     * @return
     */
    int totalCount(PageRequestDTO pageRequestDTO);

    /**
     * 게시글 등록
     *
     * @param boardDTO
     */
    void postArticle(BoardDTO boardDTO);

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    BoardDTO getArticleView(Long boardNo);

    /**
     * 게시글 조회수 증가
     * @param boardNo
     */
    void viewCount(Long boardNo);

    /**
     * 게시글 삭제
     * @param boardNo
     */
    void deleteArticle(Long boardNo);

    /**
     * 게시글 수정
     * @param boardDTO
     */
    void modifyArticle(BoardDTO boardDTO);

    /**
     * DB 비밀번호 확인
     * @param boardNo
     * @return
     */
    String dbPassword(Long boardNo);

    /**
     * 게시글 존재 여부 확인
     * @param boardNo
     * @return
     */
    Boolean findByBoardNo(Long boardNo);
}
