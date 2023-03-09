package com.mogreene.board.common.exception;

import com.mogreene.board.common.api.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: 2023/03/09 커스텀에러를 만들어서 모든 에러를 캐치할 수 없다. 그러니 런타임을 잘 활용하자
    @ExceptionHandler(RuntimeException.class)
    protected ApiResponseDTO<?> handleException(RuntimeException e) {

        log.error(e.getMessage());

        return ApiResponseDTO.builder()
                .resultType(false)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultData("에러발생!")
                .build();
    }

}
