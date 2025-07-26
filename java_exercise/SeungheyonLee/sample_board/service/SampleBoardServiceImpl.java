package com.example.monoproj.sample_board.service;

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
//        log.info("accountId: {}", createBoardRequest.getAccountId());
//
//        Account account = accountRepository.findById(createBoardRequest.getAccountId())
//                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));
//
//        log.info("account: {}", account);
//
//        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
//                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));
//
//        log.info("account profile: {}", accountProfile);

        SampleBoard board = sampleBoardRepository.save(createSampleBoardRequest.toBoard());
        return CreateSampleBoardResponse.from(board);
    }

}