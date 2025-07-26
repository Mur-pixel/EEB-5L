package com.example.demo.sample_board.service;

import com.example.demo.sample_board.service.request.CreateSampleBoardRequest;
import com.example.demo.sample_board.service.response.CreateSampleBoardResponse;

public interface SampleBoardService {
    CreateSampleBoardResponse createSampleBoard(CreateSampleBoardRequest request);
}
