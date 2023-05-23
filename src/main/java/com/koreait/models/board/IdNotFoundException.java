package com.koreait.models.board;


public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException () {
        super("게시글을 찾을 수 없습니다.");
    }
}
