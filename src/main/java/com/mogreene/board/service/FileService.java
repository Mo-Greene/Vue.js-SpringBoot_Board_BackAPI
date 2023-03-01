package com.mogreene.board.service;

import com.mogreene.board.dao.FileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileDAO fileDAO;
}
