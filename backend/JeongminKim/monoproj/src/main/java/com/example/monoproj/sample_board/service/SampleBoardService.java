package com.example.monoproj.sample_board.service;

import com.example.monoproj.board.service.request.CreateBoardRequest;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import org.springframework.stereotype.Service;

@Service
public interface SampleBoardService {
    ListSampleBoardResponse list();
    CreateSampleBoardResponse register(CreateSampleBoardRequest createSampleBoardRequest);
}
