package com.mogreene.board.dao;

import com.mogreene.board.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileDAO {
    void saveFile(FileDTO fileDTO);
    FileDTO getFile(Long fileNo);
}
