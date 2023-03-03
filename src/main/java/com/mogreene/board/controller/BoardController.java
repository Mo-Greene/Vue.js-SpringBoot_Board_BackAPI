package com.mogreene.board.controller;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.common.exception.ErrorCode;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.Pagination;
import com.mogreene.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 전체 조회
     * @param pageRequestDTO
     * @return
     */
    // TODO: 2023/03/03 다시 한번 페이지네이션 설명 듣기
    @GetMapping("/list")
    public ResponseEntity<Pagination> getArticleList(PageRequestDTO pageRequestDTO) throws CustomException {

        if (pageRequestDTO.getPage() <= 0) {
            throw new CustomException(ErrorCode.INVALID_PAGE);
        }

        Pagination list = boardService.getArticleList(pageRequestDTO);

        if (list.getDtoList().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_PAGE);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    @PostMapping("/write")
    public ResponseEntity<String> postArticle(@RequestBody @Valid BoardDTO boardDTO,
                                              BindingResult bindingResult) throws NoSuchAlgorithmException, CustomException {

        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INVALID_VALIDATION);
        }

        boardService.postArticle(boardDTO);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    @GetMapping("/{boardNo}")
    public ResponseEntity<BoardDTO> getArticleView(@PathVariable("boardNo") Long boardNo) throws CustomException{

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/{boardNo}/delete")
    public ResponseEntity<String> deleteArticle(@PathVariable("boardNo") Long boardNo) throws CustomException {

        boardService.deleteArticle(boardNo);

        return new ResponseEntity<>("Delete_ok", HttpStatus.NO_CONTENT);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     * @return
     */
    @PostMapping("/{boardNo}/modify")
    public ResponseEntity<String> modifyArticle(@PathVariable("boardNo") Long boardNo,
                                                @RequestBody @Valid BoardDTO boardDTO,
                                                BindingResult bindingResult) throws NoSuchAlgorithmException, CustomException {

        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INVALID_VALIDATION);
        }

        boardDTO.setBoardNo(boardNo);

        boardService.modifyArticle(boardDTO);

        return new ResponseEntity<>("Modify_ok", HttpStatus.OK);
    }
}
