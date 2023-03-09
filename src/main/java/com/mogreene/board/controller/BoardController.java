package com.mogreene.board.controller;

import com.mogreene.board.common.exception.CustomException;
import com.mogreene.board.common.exception.ErrorCode;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.common.api.ApiResponseDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.service.BoardService;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 게시글 전체 조회
     * @param pageRequestDTO
     * @return
     */
    // TODO: 2023/03/04 굳이 페이지 처리를 예외처리할 필요는 없다. 던지기는 해야됨
    @GetMapping("/list")
    public ApiResponseDTO<?> getArticleList(PageRequestDTO pageRequestDTO) throws CustomException {

        List<BoardDTO> list = boardService.getArticleList(pageRequestDTO);

        return ApiResponseDTO.builder()
                .resultType(true)
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData(list)
                .build();
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    @PostMapping("/write")
    public ApiResponseDTO<?> postArticle(@RequestPart @Valid BoardDTO boardDTO,
                                         @RequestPart(value = "file", required = false) MultipartFile[] multipartFile,
                                         BindingResult bindingResult) throws NoSuchAlgorithmException, IOException {

        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INVALID_VALIDATION);
        }

        Long boardNo = boardService.postArticle(boardDTO);
        fileService.uploadFile(boardNo, multipartFile);

        return ApiResponseDTO.builder()
                .resultType(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultData("Success Posting")
                .build();
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    @GetMapping("notice/{boardNo}")
    public ApiResponseDTO<BoardDTO> getArticleView(@PathVariable("boardNo") Long boardNo) throws CustomException{

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        return ApiResponseDTO.<BoardDTO>builder()
                .resultType(true)
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData(boardDTO)
                .build();
    }

    /**
     * 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("delete/{boardNo}")
    public ApiResponseDTO<?> deleteArticle(@PathVariable("boardNo") Long boardNo) throws CustomException {

        boardService.deleteArticle(boardNo);

        return ApiResponseDTO.builder()
                .resultType(true)
                .httpStatus(HttpStatus.NO_CONTENT)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultData("Delete_ok")
                .build();
    }

    /**
     * 게시글 수정
     * @param boardDTO
     * @return
     */
    @PutMapping("/modify/{boardNo}")
    public ApiResponseDTO<?> modifyArticle(@PathVariable("boardNo") Long boardNo,
                                                @RequestBody @Valid BoardDTO boardDTO,
                                                BindingResult bindingResult) throws NoSuchAlgorithmException, CustomException {

        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.INVALID_VALIDATION);
        }

        boardDTO.setBoardNo(boardNo);

        boardService.modifyArticle(boardDTO);

        return ApiResponseDTO.builder()
                .resultType(true)
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData("Modify_Ok")
                .build();
    }
}
