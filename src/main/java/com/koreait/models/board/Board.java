package com.koreait.models.board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {

    private long id;

    private String subject;

    private String content;

    private LocalDateTime regDt;
}
