package com.mogreene.board.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 커스텀 예외처리 적용시 (사용 x)
 * @author mogreene
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
