package com.mogreene.board.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * 커스텀 예외처리 적용시 (사용 x)
 * @author mogreene
 */
@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime time = LocalDateTime.now();
    private final int status;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> responseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }
}
