package com.mogreene.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

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

    private Resource resource;
    private String contentDisposition;
}
