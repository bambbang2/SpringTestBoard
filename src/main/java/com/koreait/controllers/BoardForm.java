package com.koreait.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardForm {

    private long id;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    private LocalDateTime regDt;
}
