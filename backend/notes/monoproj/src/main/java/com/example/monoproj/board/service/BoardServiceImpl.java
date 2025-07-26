package com.example.monoproj.board.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import com.example.monoproj.board.entity.Board;
import com.example.monoproj.board.repository.BoardRepository;
import com.example.monoproj.board.service.request.CreateBoardRequest;
import com.example.monoproj.board.service.request.ListBoardRequest;
import com.example.monoproj.board.service.request.UpdateBoardRequest;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.board.service.response.ListBoardResponse;
import com.example.monoproj.board.service.response.ReadBoardResponse;
import com.example.monoproj.board.service.response.UpdateBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    final private BoardRepository boardRepository;
    final private AccountRepository accountRepository;
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public ListBoardResponse list(ListBoardRequest request) {
        // PageRequest 생성 (0부터 시작하는 페이지 인덱스를 위해 page - 1을 사용)
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getPerPage());

        // 페이징된 게시글 목록을 가져오기
        Page<Board> boardPage = boardRepository.findAllWithWriter(pageRequest);

        // ListBoardResponse 객체로 변환하여 반환
        return new ListBoardResponse(boardPage.getContent(), boardPage.getTotalElements(), boardPage.getTotalPages());
    }

    @Override
    public CreateBoardResponse register(CreateBoardRequest createBoardRequest) {
        log.info("accountId: {}", createBoardRequest.getAccountId());

        Account account = accountRepository.findById(createBoardRequest.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account 존재하지 않음"));

        log.info("account: {}", account);

        AccountProfile accountProfile = accountProfileRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("AccountProfile not found"));

        log.info("account profile: {}", accountProfile);

        Board board = boardRepository.save(createBoardRequest.toBoard(accountProfile));
        return CreateBoardResponse.from(board);
    }

    @Override
    public ReadBoardResponse read(Long boardId) {
        Optional<Board> maybeBoard = boardRepository.findByIdWithWriter(boardId);

        if (maybeBoard.isEmpty()) {
            log.info("정보가 없습니다!");
            return null;
        }

        Board board = maybeBoard.get();
        return ReadBoardResponse.from(board);
    }

    @Override
    public UpdateBoardResponse update(Long boardId, Long accountId, UpdateBoardRequest updateBoardRequest) {
        Board board = boardRepository.findByIdWithWriter(boardId)
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
        if (updateBoardRequest.getTitle() != null) {
            board.setTitle(updateBoardRequest.getTitle());
        }
        if (updateBoardRequest.getContent() != null) {
            board.setContent(updateBoardRequest.getContent());
        }

        boardRepository.save(board);

        return UpdateBoardResponse.from(board);
    }

    @Override
    public void delete(Long boardId, Long accountId) {
        Board board = boardRepository.findByIdWithWriter(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!board.getWriter().getAccount().getId().equals(accountId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

}
