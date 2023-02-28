package com.mogreene.board.service;

import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDAO boardDAO;

    /**
     * 게시글 등록
     * @param boardDTO
     */
    public void postArticle(BoardDTO boardDTO) {

        boardDAO.postArticle(boardDTO);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    public BoardDTO getArticleView(Long boardNo) {

        boardDAO.viewCount(boardNo);

        return boardDAO.getArticleView(boardNo);
    }

    /**
     * 게시글 삭제
     * @param boardNo
     */
    public void deleteArticle(Long boardNo) {

        boardDAO.deleteArticle(boardNo);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     */
    public void modifyArticle(BoardDTO boardDTO) {

        boardDAO.modifyArticle(boardDTO);
    }
}
