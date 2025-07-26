package com.example.demo.mapper;

import com.example.demo.domain.Board;
import java.util.List;

public interface BoardMapper {
    Board getBoardById(Long id);
    List<Board> findAll();
    void insertBoard(Board board);
}
