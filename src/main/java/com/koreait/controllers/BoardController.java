package com.koreait.controllers;

import com.koreait.models.board.Board;
import com.koreait.models.board.BoardDao;
import com.koreait.models.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardDao boardDao;
    private final BoardService boardService;


    @GetMapping("/write")
    public String write(BoardForm boardForm, Model model) {
        return "board/write";
    }


    @GetMapping("/list")
    public String list(Model model) {
        long count = boardDao.getTotal();
        System.out.println("count = " + count);


        List<Board> items = boardService.list();
        model.addAttribute("items", items);

        return "board/list";
    }

    @PostMapping("/list")
    public String listPs(BoardForm boardForm, Errors errors, Model model) {

        try {
            boardService.delete(boardForm);
        } catch (RuntimeException e) {
            model.addAttribute("script", "alert('게시글 번호를 찾을 수 없습니다!'); history.back();");
            e.printStackTrace();
            return "commons/deleteScript.html";
        }


        return "redirect:/board/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {

        Board boardView = boardDao.get(id);
        model.addAttribute("boardView", boardView);
        return "board/view";
    }

    @PostMapping("/view/{id}")
    public String viewPs(@PathVariable long id, BoardForm boardForm, Errors errors ,Model model) {

        model.addAttribute("boardView", boardForm);
        boardService.update(boardForm);
        return "redirect:/board/list";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute BoardForm boardForm, Errors errors) {

        if(errors.hasErrors()) {
            return "board/write";
        }
        boardService.save(boardForm);

        return "redirect:/board/list";
    }
}