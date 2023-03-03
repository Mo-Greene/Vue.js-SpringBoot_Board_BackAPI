package com.mogreene.board.service;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.common.exception.ErrorCode;
import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.Pagination;
import com.mogreene.board.util.SHA512;
import com.mogreene.board.util.ServiceModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDAO boardDAO;
    private final ReplyDAO replyDAO;
    private final ServiceModule serviceModule;
    private final SHA512 sha512;

    /**
     * 게시글 조회
     * @param pageRequestDTO
     * @return
     */
    @Cacheable(value = "ArticleList", key = "#pageRequestDTO.page")
    public Pagination getArticleList(PageRequestDTO pageRequestDTO) throws CustomException {

        List<BoardDTO> list = boardDAO.getArticleList(pageRequestDTO);
        list = list.stream()
                .peek(serviceModule::modDateFormat)
                .collect(Collectors.toList());

        int total = boardDAO.totalCount(pageRequestDTO);

        return Pagination.withAll()
                .total(total)
                .dtoList(list)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    public void postArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException, CustomException {

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
    public BoardDTO getArticleView(Long boardNo) throws CustomException {

        serviceModule.validBoardNo(boardNo);

        boardDAO.viewCount(boardNo);

        BoardDTO boardDTO = boardDAO.getArticleView(boardNo);
        serviceModule.modDateFormat(boardDTO);

        boardDTO.setReplyList(replyDAO.getReplyList(boardNo));

        return boardDTO;
    }

    /**
     * 게시글 삭제
     * @param boardNo
     */
    @Caching(evict = {
            @CacheEvict(value = "ArticleList", allEntries = true),
            @CacheEvict(value = "ArticleOne", key = "#boardNo")
    })
    public void deleteArticle(Long boardNo) throws CustomException {

        serviceModule.validBoardNo(boardNo);

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
    public void modifyArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException, CustomException {

        serviceModule.validBoardNo(boardDTO.getBoardNo());
        serviceModule.checkPassword(boardDTO);

        boardDAO.modifyArticle(boardDTO);
    }
}
