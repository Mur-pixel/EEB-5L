package com.example.demo.sample_board.service;

import com.example.demo.sample_board.entity.SampleBoard;
import com.example.demo.sample_board.repository.SampleBoardRepository;
import com.example.demo.sample_board.service.request.CreateSampleBoardRequest;
import com.example.demo.sample_board.service.response.CreateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {
    private final SampleBoardRepository sampleBoardRepository;

    @Override
    public CreateSampleBoardResponse createSampleBoard(CreateSampleBoardRequest request) {
        SampleBoard sampleBoard = new SampleBoard(request.getTitle(), request.getContent());
        SampleBoard savedSampleBoard = sampleBoardRepository.save(sampleBoard);
        return CreateSampleBoardResponse.from(savedSampleBoard);
    }
}
