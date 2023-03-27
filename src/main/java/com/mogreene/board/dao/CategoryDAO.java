package com.mogreene.board.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 카테고리 DAO
 * @author mogreene
 */
@Mapper
public interface CategoryDAO {

    /**
     * 카테고리 이름
     * @param categoryNo
     * @return String categoryContent
     */
    String getCategory(int categoryNo);

}
