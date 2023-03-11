package com.mogreene.board.controller;

import com.mogreene.board.common.api.ApiResponseDTO;
import com.mogreene.board.common.status.StatusCode;
import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.page.PageRequestDTO;
import com.mogreene.board.dto.page.PageResponseDTO;
import com.mogreene.board.service.BoardService;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    // TODO: 2023/03/11 ResponseEntity 안에 감싸는 형태로 가야된다!

    private final BoardService boardService;
    private final FileService fileService;

    /**
     * 게시글 전체 조회
     * @param pageRequestDTO
     * @return
     */
    @GetMapping("/list")
    public ApiResponseDTO<?> getArticleList(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> boardList = boardService.getArticleList(pageRequestDTO);

        PageResponseDTO responseDTO = boardService.getPagination(pageRequestDTO);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("board", boardList);
        responseData.put("page", responseDTO);

        return ApiResponseDTO.builder()
                .resultType(StatusCode.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData(responseData)
                .build();
    }

    /**
     * 게시글 등록
     * @param boardDTO
     * @param bindingResult
     * @param multipartFile
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    @PostMapping("/write")
    public ApiResponseDTO<?> postArticle(@RequestPart @Valid BoardDTO boardDTO,
                                         BindingResult bindingResult,
                                         @RequestPart(value = "file", required = false) MultipartFile[] multipartFile
                                         ) throws NoSuchAlgorithmException, IOException {

        if (bindingResult.hasErrors()) {
            throw new BindingException("형식에 맞지 않습니다.");
        }

        if (multipartFile != null) {

            Long boardNo = boardService.postArticle(boardDTO);

            fileService.uploadFile(boardNo, multipartFile);

            return ApiResponseDTO.builder()
                    .resultType(StatusCode.SUCCESS)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .resultCode(HttpStatus.NO_CONTENT.value())
                    .resultData("Success Posting With Files")
                    .build();
        } else {

            boardService.postArticle(boardDTO);

            return ApiResponseDTO.builder()
                    .resultType(StatusCode.SUCCESS)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .resultCode(HttpStatus.NO_CONTENT.value())
                    .resultData("Success Posting No Files")
                    .build();
        }
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    @GetMapping("/notice/{boardNo}")
    public ApiResponseDTO<BoardDTO> getArticleView(@PathVariable("boardNo") Long boardNo) {

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        return ApiResponseDTO.<BoardDTO>builder()
                .resultType(StatusCode.SUCCESS)
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
    @DeleteMapping("/delete/{boardNo}")
    public ApiResponseDTO<?> deleteArticle(@PathVariable("boardNo") Long boardNo) {

        boardService.deleteArticle(boardNo);

        return ApiResponseDTO.builder()
                .resultType(StatusCode.SUCCESS)
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
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BindingException("형식에 맞지 않습니다.");
        }

        boardDTO.setBoardNo(boardNo);

        boardService.modifyArticle(boardDTO);

        return ApiResponseDTO.builder()
                .resultType(StatusCode.SUCCESS)
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData("Modify_Ok")
                .build();
    }

    /**
     * 비밀번호 확인 (게시글 수정 + 삭제)
     * @param boardNo
     * @param boardPassword
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/password/{boardNo}")
    public ApiResponseDTO<?> passwordCheck(@PathVariable("boardNo") Long boardNo,
                                           @RequestBody String boardPassword) throws NoSuchAlgorithmException {

        if (boardService.passwordCheck(boardNo, boardPassword) == StatusCode.SUCCESS) {

            return ApiResponseDTO.builder()
                    .resultType(StatusCode.SUCCESS)
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .resultCode(HttpStatus.NO_CONTENT.value())
                    .resultData("Valid Password")
                    .build();
        } else {

            throw new RuntimeException("Invalid Password");
        }
    }
}
