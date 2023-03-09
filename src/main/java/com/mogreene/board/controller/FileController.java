package com.mogreene.board.controller;

import com.mogreene.board.dto.FileDTO;
import com.mogreene.board.dto.api.ApiResponseDTO;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 다운로드
     * @param fileNo
     * @return
     * @throws IOException
     */
    // TODO: 2023/03/09 파일 다운로드 할땐 공통된 api 로 작성을 할 수 있을지 고민하자
    @GetMapping("/download/{fileNo}")
    public ResponseEntity<Resource> fileDown(@PathVariable("fileNo") Long fileNo) throws IOException {

        FileDTO fileDTO = fileService.downloadFile(fileNo);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, fileDTO.getContentDisposition())
                .body(fileDTO.getResource());
    }
}
