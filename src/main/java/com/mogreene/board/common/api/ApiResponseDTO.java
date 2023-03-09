package com.mogreene.board.common.api;

import com.mogreene.board.common.status.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 공통 예외처리
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {

    /* 성공여부 true, false */
    private StatusCode resultType;

    /* httpStatus */
    private HttpStatus httpStatus;

    /* http 결과 코드 */
    private Integer resultCode;

    /* 결과 데이터 */
    private T resultData;

}
