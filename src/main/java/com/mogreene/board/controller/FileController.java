package com.mogreene.board.controller;

import com.mogreene.board.common.api.ApiResponseDTO;
import com.mogreene.board.dto.FileDTO;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 파일 컨트롤러
 * @author mogreene
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 특정게시글 파일 조회
     */
    @GetMapping("/files/{boardNo}")
    public ResponseEntity<ApiResponseDTO<?>> getFileList(@PathVariable Long boardNo) {

        List<FileDTO> fileDTOS = fileService.getFilesList(boardNo);

        ApiResponseDTO<?> apiResponseDTO = ApiResponseDTO.builder()
                .httpStatus(HttpStatus.OK)
                .resultCode(HttpStatus.OK.value())
                .resultData(fileDTOS)
                .build();

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    /**
     * 파일 다운로드
     * @param fileNo
     * @return
     * @throws IOException
     */
    @GetMapping("/files/download/{fileNo}")
    public ResponseEntity<Resource> fileDown(@PathVariable("fileNo") Long fileNo) throws IOException {

        FileDTO fileDTO = fileService.downloadFile(fileNo);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, fileDTO.getContentDisposition())
                .body(fileDTO.getResource());
    }
}
