package com.mogreene.board.controller;

import com.mogreene.board.dto.FileDTO;
import com.mogreene.board.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/download/{fileNo}")
    public ResponseEntity<Resource> fileDown(@PathVariable("fileNo") Long fileNo) throws IOException {
        FileDTO fileDTO = fileService.getFile(fileNo);
        Path path = Paths.get(fileDTO.getFilePath());

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDTO.getFileOriginalName() + "\"")
                .body(resource);
    }
}
