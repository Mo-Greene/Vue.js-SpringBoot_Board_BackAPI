/* 게시글 삭제 */
DELETE FROM board
WHERE board_no >= 1;

/* 게시글 초기화 */
ALTER TABLE board AUTO_INCREMENT = 1;