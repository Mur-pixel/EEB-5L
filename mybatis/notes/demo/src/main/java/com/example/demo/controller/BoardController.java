package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardMapper boardMapper;

    @GetMapping
    public List<Board> list() {
        return boardMapper.findAll();
    }

    @GetMapping("/{id}")
    public Board get(@PathVariable Long id) {
        return boardMapper.getBoardById(id);
    }

    @PostMapping
    public String create(@RequestBody Board board) {
        boardMapper.insertBoard(board);
        return "ok";
    }
}
