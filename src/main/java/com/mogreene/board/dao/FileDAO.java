package com.mogreene.board.dao;

import com.mogreene.board.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 파일 DAO
 * @author mogreene
 */
@Mapper
public interface FileDAO {
    void saveFile(FileDTO fileDTO);
    FileDTO getFile(Long fileNo);
}
