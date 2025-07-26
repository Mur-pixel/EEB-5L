package com.example.monoproj.sample_board.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import com.example.monoproj.sample_board.entity.SampleBoard;
import com.example.monoproj.sample_board.repository.SampleBoardRepository;
import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    final private SampleBoardRepository sampleBoardRepository;
    final private AccountRepository accountRepository;
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public ListSampleBoardResponse listSampleBoard(ListSampleBoardRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getPerPage());
        Page<SampleBoard> sampleBoardPage = sampleBoardRepository.findAllWithWriter(pageRequest);
        return new ListSampleBoardResponse(sampleBoardPage.getContent(), sampleBoardPage.getTotalElements(), sampleBoardPage.getTotalPages());
    }

    @Override
    public CreateSampleBoardResponse registerSampleBoard(CreateSampleBoardRequest createSampleBoardRequest) {
        log.info("accountId: {}", createSampleBoardRequest.getAccountId());

        Account account = accountRepository.findById(createSampleBoardRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));
        log.info("account: {}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));
        log.info("accountProfile: {}", accountProfile);

        SampleBoard sampleBoard = sampleBoardRepository.save(createSampleBoardRequest.toSampleBoard(accountProfile));
        return CreateSampleBoardResponse.from(sampleBoard);
    }

    @Override
    public ReadSampleBoardResponse readSampleBoard(Long boardId) {
        Optional<SampleBoard> maybeSampleBoard = sampleBoardRepository.findByIdWithWriter(boardId);

        if (maybeSampleBoard.isEmpty()){
            log.info("정보가 없습니다!");
            return null;
        }
        SampleBoard sampleBoard = maybeSampleBoard.get();
        return ReadSampleBoardResponse.from(sampleBoard);

    }

    @Override
    public UpdateSampleBoardResponse updateSampleBoard(Long boardId, Long accountId, UpdateSampleBoardRequest updateSampleBoardRequest) {
        SampleBoard sampleBoard = sampleBoardRepository.findByIdWithWriter(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        log.info("board.getWriter(): {}", sampleBoard.getSample_Board_writer());
        log.info("board.getWriter().getAccount(): {}", sampleBoard.getSample_Board_writer().getAccount());
        log.info("board.getWriter().getAccount().getId(): {}", sampleBoard.getSample_Board_writer().getAccount().getId());

        log.info("accountId: {}", accountId);

        if (sampleBoard.getSample_Board_writer().getAccount().getId() != accountId){
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        if (updateSampleBoardRequest.getTitle() != null){
            sampleBoard.setSample_board_title(updateSampleBoardRequest.getTitle());
        }

        if (updateSampleBoardRequest.getContent() != null){
            sampleBoard.setSample_board_content(updateSampleBoardRequest.getContent());
        }
        sampleBoardRepository.save(sampleBoard);

        return UpdateSampleBoardResponse.from(sampleBoard);
    }


}