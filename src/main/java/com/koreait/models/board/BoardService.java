package com.koreait.models.board;

import com.koreait.controllers.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardDao boardDao;
    private final SaveValidator saveValidator;
    private final DeleteValidator deleteValidator;


    public void save(BoardForm boardForm) {

        saveValidator.check(boardForm);
        boardDao.insert(boardForm);
    }

    public List<Board> list(){

        return boardDao.gets();
    }

    public void update(BoardForm boardForm) {

        saveValidator.check(boardForm);
        boardDao.update(boardForm);
    }

    public void delete(BoardForm boardForm) {

        Long id = boardForm.getId();

        deleteValidator.check(boardForm);
        boardDao.delete(id);
    }
}