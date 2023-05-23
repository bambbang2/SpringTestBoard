package com.koreait.tests;

import com.koreait.controllers.BoardForm;
import com.koreait.models.board.BoardDao;
import com.koreait.models.board.BoardService;
import com.koreait.models.board.IdNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("게시글 삭제 테스트")
public class BoardDeleteTest {

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private BoardService boardService;

    private BoardForm getBoardForm(){
        BoardForm boardForm = new BoardForm();
        boardForm.setSubject("제목!!!");
        boardForm.setContent("내용!!!");

        return boardForm;
    }
    
    @Test
    @DisplayName("게시글 번호가 존재하지 않으면 IdNotFoundException 발생")
    void existSuccessTest(){
        assertThrows(IdNotFoundException.class, () -> {
            BoardForm boardForm = getBoardForm();
            boardService.delete(boardForm);
        });
    }
}
