package com.mogreene.board.controller;

import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.dto.PageDTO;
import com.mogreene.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 전체 조회
     * @param pageDTO
     * @return
     */
    // TODO: 2023/03/01 게시글 pageDTO 에 담든 해서 보내야됨 현재는 페이징 된 게시글만 보여줌
    @GetMapping("/list")
    public ResponseEntity<List<BoardDTO>> getArticleList(PageDTO pageDTO) {

        List<BoardDTO> list = boardService.getArticleList(pageDTO);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     * @param boardDTO
     */
    // TODO: 2023/02/28 예외처리와 ResponseEntity 생각
    @PostMapping("/write")
    public ResponseEntity<String> postArticle(@RequestBody BoardDTO boardDTO) throws NoSuchAlgorithmException {

        boardService.postArticle(boardDTO);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    /**
     * 게시글 상세조회
     * @param boardNo
     * @return
     */
    // TODO: 2023/02/28 없는 번호 누를시 에러처리
    @GetMapping("/{boardNo}")
    public ResponseEntity<BoardDTO> getArticleView(@PathVariable("boardNo") Long boardNo) {

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        return new ResponseEntity<>(boardDTO, HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/{boardNo}/delete")
    public ResponseEntity<String> deleteArticle(@PathVariable("boardNo") Long boardNo) {

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
                                                @RequestBody BoardDTO boardDTO) throws NoSuchAlgorithmException {

        boardDTO.setBoardNo(boardNo);

        boardService.modifyArticle(boardDTO);

        return new ResponseEntity<>("Modify_ok", HttpStatus.OK);
    }
}
