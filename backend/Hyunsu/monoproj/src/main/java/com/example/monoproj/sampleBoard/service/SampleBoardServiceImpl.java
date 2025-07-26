package com.example.monoproj.sampleBoard.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;

import com.example.monoproj.sampleBoard.entity.SampleBoard;
import com.example.monoproj.sampleBoard.repository.SampleBoardRepository;
import com.example.monoproj.sampleBoard.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sampleBoard.service.request.UpdateSampleBoardRequest;
import com.example.monoproj.sampleBoard.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.ListSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.UpdateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    final private SampleBoardRepository sampleBoardRepository;
    final private AccountRepository accountRepository;
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public ListSampleBoardResponse getAll() {
        List<SampleBoard> sampleBoards = sampleBoardRepository.findAllWithWriter();
        return new ListSampleBoardResponse(sampleBoards);
    }

    @Override
    public CreateSampleBoardResponse save(CreateSampleBoardRequest createSampleBoardRequest) {
        log.info("accountId: {}", createSampleBoardRequest.getAccountId());

        Account account = accountRepository.findById(createSampleBoardRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));

        log.info("account: {}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));

        log.info("account profile: {}", accountProfile);

        SampleBoard sampleBoard = sampleBoardRepository.save(createSampleBoardRequest.toBoard(accountProfile));
        return CreateSampleBoardResponse.from(sampleBoard);
    }

    @Override
    public ReadSampleBoardResponse read(Long sampleBoardId) {
        Optional<SampleBoard> maybeBoard = sampleBoardRepository.findByIdWithWriter(sampleBoardId);

        if (maybeBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }

        SampleBoard board = maybeBoard.get();
        return ReadSampleBoardResponse.from(board);
    }

    @Override
    public UpdateSampleBoardResponse update(Long boardId, Long accountId, UpdateSampleBoardRequest updateSampleBoardRequest) {
        SampleBoard board = sampleBoardRepository.findByIdWithWriter(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        // 작성자 검증
        log.info("board.getWriter(): {}", board.getWriter());
        log.info("board.getWriter().getAccount(): {}", board.getWriter().getAccount());
        log.info("board.getWriter().getAccount().getId(): {}", board.getWriter().getAccount().getId());

        log.info("accountId: {}", accountId);

        if (board.getWriter().getAccount().getId() != accountId) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 수정 작업 수행
        if (updateSampleBoardRequest.getTitle() != null) {
            board.setTitle(updateSampleBoardRequest.getTitle());
        }
        if (updateSampleBoardRequest.getContent() != null) {
            board.setContent(updateSampleBoardRequest.getContent());
        }

        sampleBoardRepository.save(board);

        return UpdateSampleBoardResponse.from(board);
    }
}
