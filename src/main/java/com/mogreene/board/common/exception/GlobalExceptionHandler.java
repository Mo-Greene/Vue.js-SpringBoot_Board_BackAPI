package com.mogreene.board.common.exception;

import com.mogreene.board.common.api.ApiResponseDTO;
import com.mogreene.board.common.status.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 글로벌 예외처리
 * @author mogreene
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: 2023/03/09 커스텀에러를 만들어서 모든 에러를 캐치할 수 없다. 그러니 런타임을 잘 활용하자
    // TODO: 2023/03/09 이게 맞는 예외처리라고는 생각하지 않지만 이런 방식으로 처리하는게 맞는건지

    /**
     * RuntimeException 예외처리
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    protected ApiResponseDTO<?> handleRuntimeException(RuntimeException e) {

        log.error(e.getMessage());

        return ApiResponseDTO.builder()
                .resultType(StatusCode.FAILURE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultData(e.getMessage())
                .build();
    }

    /**
     * BindingException
     * @param e
     * @return
     */
    @ExceptionHandler(BindingException.class)
    protected ApiResponseDTO<?> handleBindingException(BindingException e) {

        log.error(e.getMessage());

        return ApiResponseDTO.builder()
                .resultType(StatusCode.FAILURE)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .resultCode(HttpStatus.BAD_REQUEST.value())
                .resultData(e.getMessage())
                .build();
    }
}
