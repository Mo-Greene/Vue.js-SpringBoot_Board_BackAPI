package com.mogreene.board.dao;

import com.mogreene.board.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 파일 DAO
 * @author mogreene
 */
@Mapper
public interface FileDAO {

    /**
     * 특정게시글 파일 조회
     * @param boardNo
     * @return
     */
    List<FileDTO> getFileList(Long boardNo);

    /**
     * 파일 저장
     * @param fileDTO
     */
    void saveFile(FileDTO fileDTO);

    /**
     * 파일 다운로드
     * @param fileNo
     * @return FileDTO
     */
    FileDTO getFile(Long fileNo);
}
