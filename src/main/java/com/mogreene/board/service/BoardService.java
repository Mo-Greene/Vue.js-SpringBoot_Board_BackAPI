package com.mogreene.board.service;

import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.PageDTO;
import com.mogreene.board.util.SHA512;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDAO boardDAO;
    private final ReplyDAO replyDAO;

    /**
     * 게시글 조회
     * @param pageDTO
     * @return
     */
    public List<BoardDTO> getArticleList(PageDTO pageDTO) {

        int total = boardDAO.totalCount(pageDTO);
        pageDTO.setTotal(total);

        List<BoardDTO> list = boardDAO.getArticleList(pageDTO);

        return list;
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    public void postArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {
        SHA512 sha512 = new SHA512();

        String password = sha512.encrypt(boardDTO.getBoardPassword());

        boardDTO.setBoardPassword(password);

        boardDAO.postArticle(boardDTO);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    public BoardDTO getArticleView(Long boardNo) {

        boardDAO.viewCount(boardNo);

        BoardDTO boardDTO = boardDAO.getArticleView(boardNo);
        boardDTO.setReplyList(replyDAO.getReplyList(boardNo));

        return boardDTO;
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
    // TODO: 2023/03/01 예외처리
    public void modifyArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {
        SHA512 sha512 = new SHA512();

        String password = sha512.encrypt(boardDTO.getBoardPassword());
        String dbPassword = boardDAO.dbPassword(boardDTO);

        if (!password.equals(dbPassword)) {

            throw new RuntimeException("비밀번호 일치하지 않음");
        } else {

            boardDAO.modifyArticle(boardDTO);
        } 
    }
}
