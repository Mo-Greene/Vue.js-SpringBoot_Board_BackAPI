package com.mogreene.board.service;

import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.CategoryDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.PageResponseDTO;
import com.mogreene.board.common.util.SHA512;
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
    private final CategoryService categoryService;
    private final SHA512 sha512;

    /**
     * 게시글 조회
     * @param pageRequestDTO
     * @return
     */
    // TODO: 2023/03/04 카테고리만 캐쉬로 만들어라
    // TODO: 2023/03/04 메서드이름에 맞게
    public List<BoardDTO> getArticleList(PageRequestDTO pageRequestDTO) {

        return boardDAO.getArticleList(pageRequestDTO);
    }

    /**
     * 게시글 페이징 데이터
     * @param pageRequestDTO
     * @return
     */
    // TODO: 2023/03/09 굳이 같이 보낼 필요는 없는것 같다.
    public PageResponseDTO getArticlePaging(PageRequestDTO pageRequestDTO) {

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

        String categoryContent = categoryService.getCategoryContent(boardNo);

        boardDTO.setCategoryContent(categoryContent);
        boardDTO.setReplyList(replyDAO.getReplyList(boardNo));

        return boardDTO;
    }

    /**
     * 게시글 삭제
     * @param boardNo
     */
    // TODO: 2023/03/07 예외처리 생각해야됨
    public void deleteArticle(Long boardNo) {

        if (boardNo <= 0 || boardDAO.findByBoardNo(boardNo) == null) {
            throw new RuntimeException();
        }

        boardDAO.deleteArticle(boardNo);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     */
    // TODO: 2023/03/07 예외처리 관해선 좀 더 생각해보자
    public void modifyArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        String password = sha512.encrypt(boardDTO.getBoardPassword());
        String dbPassword = boardDAO.dbPassword(boardDTO);

        if (!password.equals(dbPassword)) {
            throw new RuntimeException();
        }

        boardDAO.modifyArticle(boardDTO);
    }
}
