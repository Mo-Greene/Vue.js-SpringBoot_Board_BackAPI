package com.mogreene.board.service;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.common.exception.ErrorCode;
import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.PageDTO;
import com.mogreene.board.util.SHA512;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Cacheable(value = "ArticleList", key = "#pageDTO.categoryNo")
    public List<BoardDTO> getArticleList(PageDTO pageDTO) {

        int total = boardDAO.totalCount(pageDTO);
        pageDTO.setTotal(total);

        return boardDAO.getArticleList(pageDTO);
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    public void postArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        String password = encryptPassword(boardDTO.getBoardPassword());

        boardDTO.setBoardPassword(password);

        boardDAO.postArticle(boardDTO);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    @Cacheable(value = "ArticleOne", key = "#boardNo")
    public BoardDTO getArticleView(Long boardNo) {

        findByBoardNo(boardNo);

        boardDAO.viewCount(boardNo);

        BoardDTO boardDTO = boardDAO.getArticleView(boardNo);
        boardDTO.setReplyList(replyDAO.getReplyList(boardNo));

        return boardDTO;
    }

    /**
     * 게시글 삭제
     * @param boardNo
     */
    // TODO: 2023/03/03 캐시 안에 같은 value 를 삭제하는 것인지 모르겠다.
    @Caching(evict = {
            @CacheEvict(value = "ArticleList", allEntries = true),
            @CacheEvict(value = "ArticleOne", key = "#boardNo")
    })
    public void deleteArticle(Long boardNo) {

        findByBoardNo(boardNo);

        boardDAO.deleteArticle(boardNo);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     */
    // TODO: 2023/03/03 CachePut 수정 후 저장된 캐시의 내용을 보여주지 않는다;
    @Caching(evict = {
            @CacheEvict(value = "ArticleList", allEntries = true),
            @CacheEvict(value = "ArticleOne", key = "#boardDTO.boardNo")
    })
    public void modifyArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        findByBoardNo(boardDTO.getBoardNo());
        checkPassword(boardDTO);

        boardDAO.modifyArticle(boardDTO);
    }

    /**
     * 비밀번호 일치 여부 메서드
     * @param boardDTO
     * @throws NoSuchAlgorithmException, CustomException
     */
    private void checkPassword(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        String password = encryptPassword(boardDTO.getBoardPassword());
        String dbPassword = boardDAO.dbPassword(boardDTO);

        if (!password.equals(dbPassword)) {
            throw new CustomException(ErrorCode.NOT_FOUND_PASSWORD);
        }
    }

    /**
     * 게시글 존재 여부 메서드
     * @param boardNo
     * @throws CustomException
     */
    private void findByBoardNo(Long boardNo) {

        if (boardNo <= 0) {
            throw new CustomException(ErrorCode.INVALID_BOARD_NO);
        }

        if (boardDAO.findByBoardNo(boardNo) == null) {

            throw new CustomException(ErrorCode.NOT_FOUND_ARTICLE);
        }
    }

    /**
     * 비밀번호 Encrypt 메서드
     * @param boardPassword
     * @return String
     * @throws NoSuchAlgorithmException
     */
    private String encryptPassword(String boardPassword) throws NoSuchAlgorithmException {

        SHA512 sha512 = new SHA512();

        return sha512.encrypt(boardPassword);
    }
}
