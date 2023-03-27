package com.mogreene.board.service;

import com.mogreene.board.common.util.SHA512;
import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.CategoryDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 게시글 Service
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDAO boardDAO;
    private final ReplyDAO replyDAO;
    private final CategoryDAO categoryDAO;
    private final SHA512 sha512;

    /**
     * 게시글 조회
     * @param pageRequestDTO
     * @return List BoardDTO
     */
    public List<BoardDTO> getArticleList(PageRequestDTO pageRequestDTO) {

        return boardDAO.getArticleList(pageRequestDTO);
    }

    /**
     * 페이지 객체
     * @param pageRequestDTO
     * @return PageResponseDTO
     */
    public PageResponseDTO getPagination(PageRequestDTO pageRequestDTO) {

        int total = boardDAO.totalCount(pageRequestDTO);

        return PageResponseDTO.withAll()
                .pageRequestDTO(pageRequestDTO)
                .total(total)
                .build();
    }

    /**
     * 게시글 등록
     * @param boardDTO
     * @return 등록된 게시글 pk
     * @throws NoSuchAlgorithmException
     */
    public Long postArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        String password = sha512.encrypt(boardDTO.getBoardPassword());
        boardDTO.setBoardPassword(password);

        boardDAO.postArticle(boardDTO);

        return boardDTO.getBoardNo();
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return BoardDTO
     */
    public BoardDTO getArticleView(Long boardNo) {

        boardDAO.viewCount(boardNo);

        BoardDTO boardDTO = boardDAO.getArticleView(boardNo);
        String categoryContent = getCategoryContent(boardDTO.getCategoryNo());
        boardDTO.setCategoryContent(categoryContent);
        boardDTO.setReplyList(replyDAO.getReplyList(boardNo));

        return boardDTO;
    }

    /**
     * 게시글 삭제
     * @param boardNo
     */
    public void deleteArticle(Long boardNo) {

        if (boardNo <= 0 || boardDAO.findByBoardNo(boardNo) == null) {
            throw new RuntimeException("없는 게시글 입니다.");
        }

        boardDAO.deleteArticle(boardNo);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     */
    public void modifyArticle(BoardDTO boardDTO) {

        boardDAO.modifyArticle(boardDTO);
    }

    /**
     * 비밀번호 확인 로직 (게시글 수정 + 삭제)
     * @param boardDTO
     * @return String Message
     * @throws NoSuchAlgorithmException
     */
    public String passwordCheck(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        String password = sha512.encrypt(boardDTO.getBoardPassword());
        String dbPassword = boardDAO.dbPassword(boardDTO.getBoardNo());

        if (!password.equals(dbPassword)) {
            log.error("비밀번호가 같지 않습니다.");

            return "FAILURE";
        } else {

            return "SUCCESS";
        }
    }

    /**
     * 카테고리 내용 캐시화
     * @param categoryNo
     * @return
     */
    @Cacheable(value = "CategoryContent", key = "#categoryNo")
    public String getCategoryContent(int categoryNo) {

        return categoryDAO.getCategory(categoryNo);
    }
}
