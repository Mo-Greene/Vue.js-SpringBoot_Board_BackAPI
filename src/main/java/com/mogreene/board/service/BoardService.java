package com.mogreene.board.service;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dao.ReplyDAO;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.Pagination;
import com.mogreene.board.util.SHA512;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final SHA512 sha512;

    /**
     * 게시글 조회
     * @param pageRequestDTO
     * @return
     */
    // TODO: 2023/03/04 카테고리만 캐쉬로 만들어라
    // TODO: 2023/03/04 메서드이름에 맞게
    public Pagination getArticleList(PageRequestDTO pageRequestDTO) throws CustomException {

        List<BoardDTO> list = boardDAO.getArticleList(pageRequestDTO);
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
    public void postArticle(BoardDTO boardDTO) throws NoSuchAlgorithmException {

        String password = sha512.encrypt(boardDTO.getBoardPassword());

        boardDTO.setBoardPassword(password);

        boardDAO.postArticle(boardDTO);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    public BoardDTO getArticleView(Long boardNo) throws CustomException {

        if (boardNo <= 0 || boardDAO.findByBoardNo(boardNo) == null) {
            throw new RuntimeException();
        }

        boardDAO.viewCount(boardNo);

        BoardDTO boardDTO = boardDAO.getArticleView(boardNo);

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
