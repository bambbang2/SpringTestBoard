package com.koreait.models.board;

import com.koreait.controllers.BoardForm;
import com.koreait.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteValidator implements Validator<BoardForm> {

    private final BoardDao boardDao;

    @Override
    public void check(BoardForm boardForm) {
        Long id = boardForm.getId();
        boolean result = boardDao.exists(id);
        existCk(result, new IdNotFoundException());
    }
}
