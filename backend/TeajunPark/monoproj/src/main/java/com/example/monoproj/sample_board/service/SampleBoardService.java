package com.example.monoproj.sample_board.service;

import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;

public interface SampleBoardService {

    ListSampleBoardResponse listSampleBoard(ListSampleBoardRequest request);
    CreateSampleBoardResponse registerSampleBoard(CreateSampleBoardRequest request);
    ReadSampleBoardResponse readSampleBoard(Long boardId);
    UpdateSampleBoardResponse updateSampleBoard(Long boardId, Long accountId, UpdateSampleBoardRequest updateSampleBoardRequest);
}
