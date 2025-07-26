package com.example.monoproj.sample_board.service;

import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;

public interface SampleBoardService {
    CreateSampleBoardResponse createSampleBoard(CreateSampleBoardRequest request);
}
