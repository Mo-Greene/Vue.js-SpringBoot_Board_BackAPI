package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

/**
 * 파일 DTO
 * @author mogreene
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    /* 파일 pk */
    private Long fileNo;

    /* 파일 실제 이름 */
    private String fileOriginalName;

    /* 파일 암호화 이름 */
    private String fileName;

    /* 파일 경로 */
    private String filePath;

    /* 파일 다운로드 리소스 */
    private Resource resource;

    /* 파일 헤더 */
    private String contentDisposition;

    /* 게시글 fk */
    private Long boardNo;
}
