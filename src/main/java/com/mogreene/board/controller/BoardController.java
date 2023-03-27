package com.mogreene.board.controller;

import com.mogreene.board.common.api.ApiResponseDTO;
import com.mogreene.board.common.util.SHA512;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.PageResponseDTO;
import com.mogreene.board.service.BoardService;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시글 컨트롤러
 * @author mogreene
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 게시글 전체 조회
     * @param pageRequestDTO
     * @return List<BoardDTO>,PageResponseDTO
     */
    @GetMapping("/boards")
    public ResponseEntity<ApiResponseDTO<?>> getArticleList(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> boardList = boardService.getArticleList(pageRequestDTO);

        PageResponseDTO responseDTO = boardService.getPagination(pageRequestDTO);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("board", boardList);
        responseData.put("page", responseDTO);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData(responseData)
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     * @param boardDTO
     * @param bindingResult
     * @param multipartFile
     * @return String Message
     * @throws IOException
     */
    @PostMapping("/boards/write")
    public ResponseEntity<ApiResponseDTO<?>> postArticle(@RequestPart @Valid BoardDTO boardDTO,
                                                         BindingResult bindingResult,
                                                         @RequestPart(value = "file", required = false) MultipartFile multipartFile
    ) throws NoSuchAlgorithmException, IOException {

        if (bindingResult.hasErrors()) {
            throw new BindingException();
        }

        if (multipartFile != null) {

            Long boardNo = boardService.postArticle(boardDTO);

            fileService.uploadFile(boardNo, multipartFile);

            ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                    .httpStatus(HttpStatus.CREATED)
                    .resultCode(HttpStatus.CREATED.value())
                    .resultData("Success Posting With Files")
                    .build();

            return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);

        } else {

            boardService.postArticle(boardDTO);

            ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                    .httpStatus(HttpStatus.CREATED)
                    .resultCode(HttpStatus.CREATED.value())
                    .resultData("Success Posting No Files")
                    .build();

            return new ResponseEntity<>(apiResponseDTO, HttpStatus.CREATED);
        }
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return boardDTO
     */
    @GetMapping("/boards/notice/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> getArticleView(@PathVariable("boardNo") Long boardNo) {

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.<BoardDTO>builder()
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData(boardDTO)
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param boardNo
     * @return HttpStatus.NO_CONTENT
     */
    @DeleteMapping("/boards/delete/{boardNo}")
    public ResponseEntity<?> deleteArticle(@PathVariable("boardNo") Long boardNo) {

        boardService.deleteArticle(boardNo);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     * @return String Message
     */
    @PutMapping("/boards/modify/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> modifyArticle(@PathVariable("boardNo") Long boardNo,
                                                           @RequestBody @Valid BoardDTO boardDTO,
                                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BindingException(String.valueOf(bindingResult.getFieldError()));
        }

        boardDTO.setBoardNo(boardNo);

        boardService.modifyArticle(boardDTO);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData("Modify_Ok")
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    /**
     * 비밀번호 확인 (게시글 수정 + 삭제)
     * @param boardNo
     * @param boardDTO
     * @return String Message
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/boards/password/{boardNo}")
    public ResponseEntity<?> passwordCheck(@PathVariable("boardNo") Long boardNo,
                                           @RequestBody BoardDTO boardDTO) throws NoSuchAlgorithmException {

        boardDTO.setBoardNo(boardNo);

        String passwordCheckResult = boardService.passwordCheck(boardDTO);

        if (passwordCheckResult.equals("SUCCESS")) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
            throw new RuntimeException("INVALID PASSWORD");
        }
    }
}
