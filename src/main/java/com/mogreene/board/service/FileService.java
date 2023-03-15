package com.mogreene.board.service;

import com.mogreene.board.dao.FileDAO;
import com.mogreene.board.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 파일 Service
 *
 * @author mogreene
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileDAO fileDAO;

    @Value("${mogreene.upload.path}")
    private String uploadPath;

    /**
     * 다중 파일 업로드
     *
     * @param files 
     */
    public void uploadFile(Long boardNo, MultipartFile files) throws IOException {

        String fileOriginalName = files.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();
        String extension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));

        String fileName = uuid + extension;
        String folderPath = makeFolder();

        String filePath = uploadPath + folderPath + fileName;

        FileDTO fileDTO = FileDTO.builder()
                .fileOriginalName(fileOriginalName)
                .fileName(fileName)
                .filePath(filePath)
                .boardNo(boardNo)
                .build();

        files.transferTo(new File(filePath));

        fileDAO.saveFile(fileDTO);

    }

    /**
     * 파일 저장시 날짜별로 폴더 만들어서 보관 메서드
     *
     * @return
     */
    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        String folderPath = str.replace("/", File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    /**
     * 파일 다운로드
     *
     * @param fileNo
     * @return
     */
    public FileDTO downloadFile(Long fileNo) throws IOException {

        FileDTO fileDTO = fileDAO.getFile(fileNo);
        UrlResource resource = new UrlResource("file:" + fileDTO.getFilePath());
        String encodeName = UriUtils.encode(fileDTO.getFileOriginalName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeName + "\"";

        fileDTO.setResource(resource);
        fileDTO.setContentDisposition(contentDisposition);

        return fileDTO;
    }

}
