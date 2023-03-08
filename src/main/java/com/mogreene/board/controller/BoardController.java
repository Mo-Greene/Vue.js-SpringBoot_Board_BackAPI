package com.mogreene.board.controller;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.common.exception.ErrorCode;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.Pagination;
import com.mogreene.board.service.BoardService;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    // TODO: 2023/03/04 리턴 responseApi를 생각해서 만들어보자
    // TODO: 2023/03/04 responseApi 안에 result 생각하고 resultCode 생각하자
    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 게시글 전체 조회
     * @param pageRequestDTO
     * @return
     */
    // TODO: 2023/03/03 다시 한번 페이지네이션 설명 듣기
    // TODO: 2023/03/04 굳이 페이지 처리를 예외처리할 필요는 없다. 던지기는 해야됨
    @GetMapping("/list")
    public ResponseEntity<Pagination> getArticleList(PageRequestDTO pageRequestDTO) throws CustomException {

        Pagination list = boardService.getArticleList(pageRequestDTO);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    @PostMapping("/write")
    public ResponseEntity<String> postArticle(@RequestPart @Valid BoardDTO boardDTO,
                                              @RequestPart(value = "file", required = false) MultipartFile[] multipartFile,
                                              BindingResult bindingResult) throws CustomException, NoSuchAlgorithmException, IOException {

        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INVALID_VALIDATION);
        }

        Long boardNo = boardService.postArticle(boardDTO);
        fileService.uploadFile(boardNo, multipartFile);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    @GetMapping("notice/{boardNo}")
    public ResponseEntity<BoardDTO> getArticleView(@PathVariable("boardNo") Long boardNo) throws CustomException{

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("delete/{boardNo}")
    public ResponseEntity<String> deleteArticle(@PathVariable("boardNo") Long boardNo) throws CustomException {

        boardService.deleteArticle(boardNo);

        return new ResponseEntity<>("Delete_ok", HttpStatus.NO_CONTENT);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     * @return
     */
    @PutMapping("/modify/{boardNo}")
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
