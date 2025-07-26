package com.example.monoproj.board.service;

import com.example.monoproj.account.entity.*;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import com.example.monoproj.board.entity.Board;
import com.example.monoproj.board.repository.BoardRepository;
import com.example.monoproj.board.service.request.CreateBoardRequest;
import com.example.monoproj.board.service.request.ListBoardRequest;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardServiceTests {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountProfileRepository accountProfileRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    private Account account;
    private AccountProfile accountProfile;
    private Board board;
    private List<Board> boardList;

    public static Account createAccountWithId(Long id) {
        AccountRoleType roleType = new AccountRoleType(RoleType.NORMAL);
        AccountLoginType loginType = new AccountLoginType(LoginType.KAKAO);
        Account account = new Account(roleType, loginType);
        ReflectionTestUtils.setField(account, "id", id);
        return account;
    }

    public static AccountProfile createProfileWithId(Long id, Account account) {
        AccountProfile profile = new AccountProfile(account, "tester", "tester@email.com");
        ReflectionTestUtils.setField(profile, "id", id);
        return profile;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = createAccountWithId(100L);
        accountProfile = createProfileWithId(100L, account);

        board = new Board("기존 제목", accountProfile, "기존 내용");
        ReflectionTestUtils.setField(board, "boardId", 1L);

        boardList = IntStream.rangeClosed(1, 21)
                .mapToObj(i -> {
                    Board b = new Board("제목" + i, accountProfile, "내용" + i);
                    ReflectionTestUtils.setField(b, "boardId", (long) i);
                    return b;
                })
                .collect(Collectors.toList());
    }

    @Test
    void 게시글_생성_성공() {
        CreateBoardRequest request = new CreateBoardRequest("제목", 100L, "내용");

        when(accountRepository.findById(100L)).thenReturn(Optional.of(account));
        when(accountProfileRepository.findByAccount(account)).thenReturn(Optional.of(accountProfile));
        when(boardRepository.save(any(Board.class))).thenReturn(board);

        CreateBoardResponse response = boardService.register(request);

        assertEquals(board.getBoardId(), response.getBoardId());
        assertEquals(board.getTitle(), response.getTitle());
        assertEquals(board.getContent(), response.getContent());

        ArgumentCaptor<Board> boardCaptor = ArgumentCaptor.forClass(Board.class);
        verify(boardRepository).save(boardCaptor.capture());

        Board savedBoard = boardCaptor.getValue();
        assertEquals(request.getTitle(), savedBoard.getTitle());
        assertEquals(request.getContent(), savedBoard.getContent());
        assertEquals(accountProfile, savedBoard.getWriter());
    }

    @Test
    void 게시글_조회_성공() {
        when(boardRepository.findByIdWithWriter(1L)).thenReturn(Optional.of(board));

        var response = boardService.read(1L);

        assertEquals(board.getBoardId(), response.getBoardId());
        assertEquals(board.getTitle(), response.getTitle());
        assertEquals(board.getContent(), response.getContent());
        assertEquals(board.getWriter().getNickname(), response.getNickname());
    }

    @Test
    void 게시글_수정_성공() {
        when(boardRepository.findByIdWithWriter(1L)).thenReturn(Optional.of(board));

        String newTitle = "수정된 제목";
        String newContent = "수정된 내용";

        var request = new com.example.monoproj.board.service.request.UpdateBoardRequest(newTitle, newContent);
        var response = boardService.update(1L, 100L, request);

        assertEquals(newTitle, response.getTitle());
        assertEquals(newContent, response.getContent());

        verify(boardRepository).save(board);
    }

    @Test
    void 게시글_삭제_성공() {
        when(boardRepository.findByIdWithWriter(1L)).thenReturn(Optional.of(board));

        boardService.delete(1L, 100L);

        verify(boardRepository).delete(board);
    }

    @Test
    void 게시글_리스트_첫페이지_성공() {
        List<Board> paginationedBoardList = boardList.subList(0, 10);
        Page<Board> boardPage = new PageImpl<>(paginationedBoardList, PageRequest.of(0, 10), 21);

        when(boardRepository.findAllWithWriter(PageRequest.of(0, 10))).thenReturn(boardPage);

        var request = new ListBoardRequest(1, 10);
        var response = boardService.list(request);

        assertEquals(21, response.getTotalItems());
        assertEquals(3, response.getTotalPages());
        assertEquals(10, response.getBoardList().size());
        assertEquals("제목1", response.getBoardList().get(0).getTitle());
    }

    @Test
    void 게시글_리스트_중간페이지_성공() {
        List<Board> paginationedBoardList = boardList.subList(10, 20);
        Page<Board> boardPage = new PageImpl<>(paginationedBoardList, PageRequest.of(1, 10), 21);

        when(boardRepository.findAllWithWriter(PageRequest.of(1, 10))).thenReturn(boardPage);

        var request = new ListBoardRequest(2, 10);
        var response = boardService.list(request);

        assertEquals(3, response.getTotalPages());
        assertEquals(10, response.getBoardList().size());
        assertEquals("제목11", response.getBoardList().get(0).getTitle());
    }

    @Test
    void 게시글_리스트_마지막페이지_낱개_성공() {
        List<Board> paginationedBoardList = boardList.subList(20, 21);
        Page<Board> boardPage = new PageImpl<>(paginationedBoardList, PageRequest.of(2, 10), 21);

        when(boardRepository.findAllWithWriter(PageRequest.of(2, 10))).thenReturn(boardPage);

        var request = new ListBoardRequest(3, 10);
        var response = boardService.list(request);

        assertEquals(3, response.getTotalPages());
        assertEquals(1, response.getBoardList().size());
        assertEquals("제목21", response.getBoardList().get(0).getTitle());
    }

    @Test
    void 게시글_리스트_빈페이지_조회() {
        List<Board> emptyList = List.of();
        Page<Board> boardPage = new PageImpl<>(emptyList, PageRequest.of(3, 10), 21);

        when(boardRepository.findAllWithWriter(PageRequest.of(3, 10))).thenReturn(boardPage);

        var request = new ListBoardRequest(4, 10);
        var response = boardService.list(request);

        assertEquals(3, response.getTotalPages());
        assertEquals(0, response.getBoardList().size());
    }
}
