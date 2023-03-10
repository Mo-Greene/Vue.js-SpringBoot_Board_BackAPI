package com.mogreene.board.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 공통 responseDTO
 * @param <T>
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDTO<T> {

    /* httpStatus */
    private HttpStatus httpStatus;

    /* http 결과 코드 */
    private Integer resultCode;

    /* 결과 데이터 */
    private T resultData;

}
