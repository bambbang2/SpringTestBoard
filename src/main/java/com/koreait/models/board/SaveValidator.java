package com.koreait.models.board;

import com.koreait.controllers.BoardForm;
import com.koreait.validators.Validator;
import org.springframework.stereotype.Component;

@Component
public class SaveValidator implements Validator<BoardForm> {
    @Override
    public void check(BoardForm boardForm) {
        requiredCk(boardForm.getSubject(), new BoardValidaException("제목을 입력해주세용!"));
        requiredCk(boardForm.getContent(), new BoardValidaException("내용을 입력해주세용!"));
    }
}
