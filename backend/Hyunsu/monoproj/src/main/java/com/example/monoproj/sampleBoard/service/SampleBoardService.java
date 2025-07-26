package com.example.monoproj.sampleBoard.service;

import com.example.monoproj.sampleBoard.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sampleBoard.service.request.UpdateSampleBoardRequest;
import com.example.monoproj.sampleBoard.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.ListSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.UpdateSampleBoardResponse;

public interface SampleBoardService {
    ListSampleBoardResponse getAll();
    CreateSampleBoardResponse save(CreateSampleBoardRequest createSampleBoardRequest);
    ReadSampleBoardResponse read(Long boardId);
    UpdateSampleBoardResponse update(Long boardId, Long accountId, UpdateSampleBoardRequest updateBoardRequest);
}
