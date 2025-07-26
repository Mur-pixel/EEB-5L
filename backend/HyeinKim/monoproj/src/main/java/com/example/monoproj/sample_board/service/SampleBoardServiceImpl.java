package com.example.monoproj.sample_board.service;

import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.sample_board.entity.SampleBoard;
import com.example.monoproj.sample_board.repository.SampleBoardRepository;
import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    final private SampleBoardRepository sampleBoardRepository;

    @Override
    public CreateSampleBoardResponse register(CreateSampleBoardRequest createSampleBoardRequest) {

        SampleBoard sampleBoard = sampleBoardRepository.save(createSampleBoardRequest.toSampleBoard());
        return CreateSampleBoardResponse.from(sampleBoard);
    }
}
