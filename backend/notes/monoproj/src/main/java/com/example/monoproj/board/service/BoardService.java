package com.example.monoproj.board.service;

import com.example.monoproj.board.service.request.CreateBoardRequest;
import com.example.monoproj.board.service.request.ListBoardRequest;
import com.example.monoproj.board.service.request.UpdateBoardRequest;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.board.service.response.ListBoardResponse;
import com.example.monoproj.board.service.response.ReadBoardResponse;
import com.example.monoproj.board.service.response.UpdateBoardResponse;

public interface BoardService {
    ListBoardResponse list(ListBoardRequest request);
    CreateBoardResponse register(CreateBoardRequest createBoardRequest);
    ReadBoardResponse read(Long boardId);
    UpdateBoardResponse update(Long boardId, Long accountId, UpdateBoardRequest updateBoardRequest);
    void delete(Long boardId, Long accountId);
}
