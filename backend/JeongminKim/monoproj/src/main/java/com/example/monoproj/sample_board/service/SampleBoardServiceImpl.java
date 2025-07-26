package com.example.monoproj.sample_board.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.board.entity.Board;
import com.example.monoproj.board.service.request.CreateBoardRequest;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.sample_board.entity.SampleBoard;
import com.example.monoproj.sample_board.repository.SampleBoardRepository;
import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    final private SampleBoardRepository sampleBoardRepository;

    @Override
    public ListSampleBoardResponse list() {

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<SampleBoard> boardPage = sampleBoardRepository.findAllWithWriter(pageRequest);

        return new ListSampleBoardResponse(boardPage.getContent(), boardPage.getTotalElements(), boardPage.getTotalPages());
    }

    @Override
    public CreateSampleBoardResponse register(CreateSampleBoardRequest createSampleBoardRequest) {
        SampleBoard sampleBoard = sampleBoardRepository.save(createSampleBoardRequest.toSampleBoard());
        return CreateSampleBoardResponse.from(sampleBoard);
    }

}
