package com.mogreene.board.service;

import com.mogreene.board.dao.CategoryDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDAO categoryDAO;

    /**
     * 카테고리 내용 캐시화
     * @param boardNo
     * @return
     */
    @Cacheable(value = "CategoryContent", key = "#boardNo")
    public String getCategoryContent(Long boardNo) {

        return categoryDAO.getCategoryNum(boardNo);
    }

}
