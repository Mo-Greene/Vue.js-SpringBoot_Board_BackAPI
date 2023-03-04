package com.mogreene.board.util;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.common.exception.ErrorCode;
import com.mogreene.board.dao.BoardDAO;
import com.mogreene.board.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class ServiceModule {
    private final SHA512 sha512;
    // TODO: 2023/03/04 boardDAO 생각 
    private final BoardDAO boardDAO;

    /**
     * 게시글 존재 여부 메서드
     * @param boardNo
     * @throws CustomException
     */
    public void validBoardNo(Long boardNo) throws CustomException {

        if (boardNo <= 0) {
            throw new CustomException(ErrorCode.INVALID_BOARD_NO);
        }

        if (boardDAO.findByBoardNo(boardNo) == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_ARTICLE);
        }
    }

    /**
     * 비밀번호 일치 여부 메서드
     * @param boardDTO
     * @throws NoSuchAlgorithmException, CustomException
     */
    public void checkPassword(BoardDTO boardDTO) throws NoSuchAlgorithmException, CustomException {

        String password = sha512.encrypt(boardDTO.getBoardPassword());
        String dbPassword = boardDAO.dbPassword(boardDTO);

        if (!password.equals(dbPassword)) {
            throw new CustomException(ErrorCode.NOT_FOUND_PASSWORD);
        }
    }

    /**
     * 생성일자, 수정일자 format
     * @param boardDTO
     */
    public void modDateFormat(BoardDTO boardDTO) {

        // TODO: 2023/03/04 null체크 좀 더 
        if (boardDTO.getBoardModDate() == null) {
            boardDTO.setBoardModDate("-");
        }
    }
}
