package com.example.monoproj.board.controller;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.board.controller.request_form.CreateBoardRequestForm;
import com.example.monoproj.board.controller.request_form.ListBoardRequestForm;
import com.example.monoproj.board.controller.request_form.UpdateBoardRequestForm;
import com.example.monoproj.board.controller.response_form.CreateBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ListBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ReadBoardResponseForm;
import com.example.monoproj.board.controller.response_form.UpdateBoardResponseForm;
import com.example.monoproj.board.entity.Board;
import com.example.monoproj.board.service.BoardService;
import com.example.monoproj.board.service.BoardServiceTests;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.board.service.response.ListBoardResponse;
import com.example.monoproj.board.service.response.ReadBoardResponse;
import com.example.monoproj.board.service.response.UpdateBoardResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardControllerTests {

    @InjectMocks
    private BoardController boardController;

    @Mock
    private BoardService boardService;

    @Mock
    private RedisCacheService redisCacheService;

    @Test
    void 게시글_리스트_조회_성공() {
        // Account, AccountProfile, Board 엔티티 생성 및 필드 세팅
        Account account = BoardServiceTests.createAccountWithId(100L);
        AccountProfile profile = BoardServiceTests.createProfileWithId(200L, account);

        Board board = new Board("테스트 제목", profile, "테스트 내용");
        ReflectionTestUtils.setField(board, "boardId", 1L);
        ReflectionTestUtils.setField(board, "createDate", LocalDateTime.of(2025,7,16,10,30));

        List<Board> boardList = List.of(board);

        // ListBoardResponse 생성
        ListBoardResponse serviceResponse = new ListBoardResponse(boardList, 1, 1);

        // 서비스가 위 응답 반환하도록 목 설정
        when(boardService.list(any())).thenReturn(serviceResponse);

        // 요청 폼 생성
        ListBoardRequestForm requestForm = new ListBoardRequestForm(1, 10);
        ListBoardResponseForm responseForm = boardController.boardList(requestForm);

        // 검증: 총 아이템, 페이지 수, 리스트 크기
        assertEquals(1, responseForm.getTotalItems());
        assertEquals(1, responseForm.getTotalPages());
        assertEquals(1, responseForm.getBoardList().size());

        Map<String, Object> boardMap = responseForm.getBoardList().get(0);
        assertEquals(board.getBoardId(), boardMap.get("boardId"));
        assertEquals(board.getTitle(), boardMap.get("title"));
        assertEquals(board.getContent(), boardMap.get("content"));
        assertEquals(profile.getNickname(), boardMap.get("nickname"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String expectedDate = board.getCreateDate().format(formatter);
        assertEquals(expectedDate, boardMap.get("createDate"));
    }

    @Test
    void 게시글_생성_성공() {
        String bearerToken = "Bearer mockToken123";
        String token = bearerToken.replace("Bearer ", "").trim();
        Long ACCOUNT_ID = 100L;

        CreateBoardRequestForm form = new CreateBoardRequestForm("제목", "내용");
        CreateBoardResponse serviceResponse = new CreateBoardResponse(1L, "제목", "내용", "작성자", LocalDateTime.now());

        when(redisCacheService.getValueByKey(token, Long.class)).thenReturn(ACCOUNT_ID);
        when(boardService.register(any())).thenReturn(serviceResponse);

        CreateBoardResponseForm result = boardController.registerBoard(bearerToken, form);

        assertEquals("제목", result.getTitle());
        assertEquals("내용", result.getContent());

        verify(redisCacheService).getValueByKey(token, Long.class);
        verify(boardService).register(argThat(req ->
                "제목".equals(req.getTitle()) && "내용".equals(req.getContent()) && ACCOUNT_ID.equals(req.getAccountId())
        ));
    }

    @Test
    void 게시글_조회_성공() {
        Long boardId = 1L;
        String expectedTitle = "제목";
        String expectedContent = "내용";
        String expectedNickname = "작성자";
        LocalDateTime expectedCreateDate = LocalDateTime.of(2025, 7, 16, 10, 30);

        ReadBoardResponse response = new ReadBoardResponse(
                boardId,
                expectedTitle,
                expectedNickname,
                expectedContent,
                expectedCreateDate
        );

        when(boardService.read(boardId)).thenReturn(response);

        ReadBoardResponseForm result = boardController.readBoard(boardId);

        assertEquals(expectedTitle, result.getTitle());
        assertEquals(expectedContent, result.getContent());
        assertEquals(expectedNickname, result.getNickname());
        assertEquals(expectedCreateDate, result.getCreateDate());

        verify(boardService).read(boardId);
    }

    @Test
    void 게시글_수정_성공() {
        Long boardId = 1L;
        String bearerToken = "Bearer mockToken456";
        String token = bearerToken.replace("Bearer ", "").trim();
        Long accountId = 100L;

        String updatedTitle = "수정제목";
        String updatedContent = "수정내용";
        String expectedNickname = "작성자닉네임";
        LocalDateTime expectedCreateDate = LocalDateTime.of(2025, 7, 16, 10, 30);

        UpdateBoardRequestForm form = new UpdateBoardRequestForm(updatedTitle, updatedContent, null);

        UpdateBoardResponse mockResponse = new UpdateBoardResponse(
                boardId,
                updatedTitle,
                expectedNickname,
                updatedContent,
                expectedCreateDate
        );

        when(redisCacheService.getValueByKey(token, Long.class)).thenReturn(accountId);
        when(boardService.update(eq(boardId), eq(accountId), any())).thenReturn(mockResponse);

        UpdateBoardResponseForm result = boardController.updateBoard(boardId, form, bearerToken);

        assertEquals(updatedTitle, result.getTitle());
        assertEquals(updatedContent, result.getContent());
        assertEquals(expectedNickname, result.getNickname());
        assertEquals(expectedCreateDate, result.getCreateDate());

        verify(redisCacheService).getValueByKey(token, Long.class);
        verify(boardService).update(eq(boardId), eq(accountId), any());
    }

    @Test
    void 게시글_삭제_성공() {
        Long boardId = 1L;
        String token = "Bearer mockToken123";
        Long accountId = 100L;

        when(redisCacheService.getValueByKey("mockToken123", Long.class)).thenReturn(accountId);

        doNothing().when(boardService).delete(boardId, accountId);

        boardController.deleteBoard(boardId, token);

        verify(redisCacheService).getValueByKey("mockToken123", Long.class);
        verify(boardService).delete(boardId, accountId);
    }

    @Test
    void 게시글_삭제_존재하지않음_예외() {
        Long boardId = 999L;
        String token = "Bearer mockToken123";
        Long accountId = 100L;

        when(redisCacheService.getValueByKey("mockToken123", Long.class)).thenReturn(accountId);

        doThrow(new RuntimeException("게시글이 존재하지 않습니다."))
                .when(boardService).delete(boardId, accountId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            boardController.deleteBoard(boardId, token);
        });

        assertEquals("게시글이 존재하지 않습니다.", exception.getMessage());

        verify(redisCacheService).getValueByKey("mockToken123", Long.class);
        verify(boardService).delete(boardId, accountId);
    }
}

