package com.mogreene.board.common.exception;

import com.mogreene.board.common.api.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 글로벌 예외처리
 * @author mogreene
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * RuntimeException 예외처리
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ApiResponseDTO<?>> handleRuntimeException(RuntimeException e) {

        log.error(e.getMessage());

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultData(e.getMessage())
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * BindingException
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    protected ResponseEntity<ApiResponseDTO<?>> handleBindingException(BindingException e) {

        log.error(e.getMessage());

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultData(e.getMessage())
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.BAD_REQUEST);
    }
}
