package com.mogreene.board.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryDAO {

    /**
     * 카테고리 내용
     */
    String getCategoryNum(Long boardNo);

}
