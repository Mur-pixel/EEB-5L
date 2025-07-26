package com.example.monoproj.sample_board.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import com.example.monoproj.board.service.request.CreateBoardRequest;
import com.example.monoproj.sample_board.entitiy.SampleBoard;
import com.example.monoproj.sample_board.repository.SampleBoardRepository;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    private final AccountRepository accountRepository;
    private final AccountProfileRepository accountProfileRepository;
    private final SampleBoardRepository sampleBoardRepository;

    @Override
    public ListSampleBoardResponse listSampleBoard() {

        List<SampleBoard> all = sampleBoardRepository.findAll();

        return new ListSampleBoardResponse(all);
    }

    @Override
    public CreateSampleBoardResponse register(CreateBoardRequest createBoardRequest) {
        log.info("accountId:{}", createBoardRequest.getAccountId());

        Account account = accountRepository.findById(createBoardRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("account not found"));

        log.info("account:{}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("account profile not found"));

        log.info("accountProfile:{}", accountProfile);

        SampleBoard sampleBoard= SampleBoardRepository.save(createBoardRequest.toBoard(accountProfile));

        return CreateSampleBoardResponse.from(sampleBoard);
    }


}
