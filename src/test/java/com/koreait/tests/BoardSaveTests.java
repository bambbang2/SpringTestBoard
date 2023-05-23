package com.koreait.tests;

import com.koreait.controllers.BoardForm;
import com.koreait.models.board.Board;
import com.koreait.models.board.BoardDao;
import com.koreait.models.board.BoardService;
import com.koreait.models.board.BoardValidaException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("게시판 등록 테스트")
public class BoardSaveTests {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardDao boardDao;

    private BoardForm getBoardForm(){
        BoardForm boardForm = new BoardForm();
        boardForm.setSubject("제목!!!");
        boardForm.setContent("내용!!!");

        return boardForm;
    }


    @Test
    @DisplayName("게시글 등록 성공 시 예외X")
    void saveSuccessTest(){
        assertDoesNotThrow(() -> {
            BoardForm boardForm = getBoardForm();
            boardService.save(boardForm);
        });
    }

    @Test
    @DisplayName("필수항목 테스트")
    void requiredFieldsCk(){
        assertAll(
                () -> {
                    BoardValidaException thrown = assertThrows(BoardValidaException.class, () -> {
                        BoardForm boardForm = getBoardForm();
                        boardForm.setSubject(null);
                        boardService.save(boardForm);
                    });

                    System.out.println(thrown.getMessage());
                    assertTrue(thrown.getMessage().contains("제목을 입력"));

                }, () -> {
                    BoardValidaException thrown = assertThrows(BoardValidaException.class, () -> {
                        BoardForm boardForm = getBoardForm();
                        boardForm.setSubject("     ");
                        boardService.save(boardForm);
                    });

                    System.out.println(thrown.getMessage());
                    assertTrue(thrown.getMessage().contains("제목을 입력"));

                }, () -> {
                    BoardValidaException thrown = assertThrows(BoardValidaException.class, () -> {
                        BoardForm boardForm = getBoardForm();
                        boardForm.setContent(null);
                        boardService.save(boardForm);
                    });

                    System.out.println(thrown.getMessage());
                    assertTrue(thrown.getMessage().contains("내용을 입력"));

                }, () -> {
                    BoardValidaException thrown = assertThrows(BoardValidaException.class, () -> {
                        BoardForm boardForm = getBoardForm();
                        boardForm.setContent("      ");
                        boardService.save(boardForm);
                    });

                    System.out.println(thrown.getMessage());
                    assertTrue(thrown.getMessage().contains("내용을 입력"));
                }
        );
    }

    @Test
    @DisplayName("등록한 게시글이 일치하는지 체크")
    void registerEqTest(){
        BoardForm boardForm = getBoardForm();

        assertDoesNotThrow(() -> {
            boardDao.insert(boardForm);
        });
        Long id = boardForm.getId();
        if (id == null){ // 등록된 게시글이 없으면 실패
            fail();
        }

        Board board = boardDao.get(id);
        if (board == null){ // 조회된 게시글이 없으면
            fail();
        }
        assertEquals(boardForm.getSubject(), board.getSubject());
        assertEquals(boardForm.getContent(), board.getContent());
    }
}