package com.example.monoproj.board.service;

import com.example.monoproj.board.service.request.ListBoardRequest;
import com.example.monoproj.board.service.response.ListBoardResponse;

public interface BoardService {
    ListBoardResponse list(ListBoardRequest request);
}
