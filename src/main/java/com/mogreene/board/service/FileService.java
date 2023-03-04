package com.mogreene.board.service;

import com.mogreene.board.dao.FileDAO;
import com.mogreene.board.dto.FileDTO;
import com.mogreene.board.util.MD5;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileDAO fileDAO;
    private final MD5 md5;

    public Long saveFile(MultipartFile multipartFile) throws NoSuchAlgorithmException, IOException {

        String fileOriginalName = multipartFile.getOriginalFilename();
        String fileName = md5.md5generate(fileOriginalName);
        String fileDestination = "/Users/mogreene/Desktop/files/";
        if (!new File(fileDestination).exists()) {
            try {
                new File(fileDestination).mkdir();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        String filePath = fileDestination + fileName;

        multipartFile.transferTo(new File(filePath));

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileOriginalName(fileOriginalName);
        fileDTO.setFileName(fileName);
        fileDTO.setFilePath(filePath);

        return fileDAO.saveFile(fileDTO);
    }

    public FileDTO getFile(Long fileNo) {

        return fileDAO.getFile(fileNo);
    }
}
