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

    /**
     * 공통 response httpStatus
     */
    private HttpStatus httpStatus;

    /**
     * 공통 response resultCode
     */
    private Integer resultCode;

    /**
     * 공통 response resultData
     */
    private T resultData;

}
