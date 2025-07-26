package com.example.monoproj.sample_board.service;

import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;

public interface SampleBoardService {

    ListSampleBoardResponse list(ListSampleBoardRequest request);

    CreateSampleBoardResponse register(CreateSampleBoardRequest createSampleBoardRequest);

    ReadSampleBoardResponse read(Long sampleBoardId);

    UpdateSampleBoardResponse update(Long sampleBoardId, String nickname, UpdateSampleBoardRequest updateRequest);
}
