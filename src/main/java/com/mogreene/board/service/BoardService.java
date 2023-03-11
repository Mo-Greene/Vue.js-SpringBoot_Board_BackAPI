package com.mogreene.board.service;

import com.mogreene.board.common.status.StatusCode;
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
     * @return
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
     * @return
     */
    public BoardDTO getArticleView(Long boardNo) {

        boardDAO.viewCount(boardNo);

        BoardDTO boardDTO = boardDAO.getArticleView(boardNo);

        // TODO: 2023/03/11 캐쉬 수정
        String categoryContent = getCategoryContent(boardNo);

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
    // TODO: 2023/03/07 예외처리 관해선 좀 더 생각해보자
    public void modifyArticle(BoardDTO boardDTO) {

        boardDAO.modifyArticle(boardDTO);
    }

    /**
     * 비밀번호 확인 로직 (게시글 수정 + 삭제)
     * @param boardDTO
     * @return StatusCode
     * @throws NoSuchAlgorithmException
     */
    public StatusCode passwordCheck(Long boardNo, String boardPassword) throws NoSuchAlgorithmException {

        String password = sha512.encrypt(boardPassword);
        String dbPassword = boardDAO.dbPassword(boardNo);

        if (!password.equals(dbPassword)) {
            log.error("비밀번호가 같지 않습니다.");

            return StatusCode.FAILURE;
        } else
            return StatusCode.SUCCESS;
    }

    /**
     * 카테고리 내용 캐시화
     * @param boardNo
     * @return
     */
    // TODO: 2023/03/10 categoryDAO를 가져왔지만 따로 service를 만들지 않고 맵핑함 => 서비스의 관계를 걸면 안된다고 생각해서
    // TODO: 2023/03/11 이거 완전 실패 게시판 캐시들을 정확히 이해하고 사용하자
    @Cacheable(value = "CategoryContent", key = "#boardNo")
    public String getCategoryContent(Long boardNo) {

        return categoryDAO.getCategoryNum(boardNo);
    }
}
