package com.mogreene.board.controller;

import com.mogreene.board.dto.BoardDTO;
import com.mogreene.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 등록
     * @param boardDTO
     */
    // TODO: 2023/02/28 예외처리와 ResponseEntity 생각
    @PostMapping("/write")
    public ResponseEntity postArticle(@RequestBody BoardDTO boardDTO) {

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
    public BoardDTO getArticleView(@PathVariable("boardNo") Long boardNo) {

        BoardDTO boardDTO = boardService.getArticleView(boardNo);

        return boardDTO;
    }

    /**
     * 게시글 삭제
     * @param boardNo
     * @return
     */
    @DeleteMapping("/{boardNo}/delete")
    public ResponseEntity deleteArticle(@PathVariable("boardNo") Long boardNo) {

        boardService.deleteArticle(boardNo);

        return new ResponseEntity<>("Delete_ok", HttpStatus.NO_CONTENT);
    }

    /**
     * 게시글 수정
     * @param boardDTO
     * @return
     */
    @PostMapping("/{boardNo}/modify")
    public ResponseEntity modifyArticle(BoardDTO boardDTO) {

        boardService.modifyArticle(boardDTO);

        return new ResponseEntity<>("Modify_ok", HttpStatus.OK);
    }
}
