package com.mogreene.board.service;

import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.PageDTO;
import com.mogreene.board.util.SHA512;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
    @Cacheable(value = "ArticleOne", key = "#boardNo")
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
    // TODO: 2023/03/03 캐시 컨테이너 같은게 있다고 하는데 그 안에 같은 value 를 삭제하는 것인지 모르겠다.
    @Caching(evict = {
            @CacheEvict(value = "ArticleList", allEntries = true),
            @CacheEvict(value = "ArticleOne", key = "#boardNo")
    })
    public void deleteArticle(Long boardNo) {

        boardDAO.deleteArticle(boardNo);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     */
    // TODO: 2023/03/01 예외처리
    // TODO: 2023/03/03 CachePut 수정 후 저장된 캐시의 내용을 보여주지 않는다;
    @Caching(evict = {
            @CacheEvict(value = "ArticleList", allEntries = true),
            @CacheEvict(value = "ArticleOne", key = "#boardDTO.boardNo")
    })
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
