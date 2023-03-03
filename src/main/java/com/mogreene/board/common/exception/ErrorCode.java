package com.mogreene.board.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_VALIDATION(HttpStatus.BAD_REQUEST, "게시글 양식에 맞춰 적어주세요."),
    INVALID_BOARD_NO(HttpStatus.BAD_REQUEST, "잘못된 게시글 정보입니다."),

    /* 404 NOT_FOUND : no resource */
    NOT_FOUND_ARTICLE(HttpStatus.NOT_FOUND, "찾을 수 없는 게시글입니다."),
    NOT_FOUND_PASSWORD(HttpStatus.NOT_FOUND, "비밀번호가 같지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
