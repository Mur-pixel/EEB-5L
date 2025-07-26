package com.example.monoproj.board.controller;

import com.example.monoproj.board.controller.request_form.CreateBoardRequestForm;
import com.example.monoproj.board.controller.request_form.ListBoardRequestForm;
import com.example.monoproj.board.controller.request_form.UpdateBoardRequestForm;
import com.example.monoproj.board.controller.response_form.CreateBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ListBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ReadBoardResponseForm;
import com.example.monoproj.board.controller.response_form.UpdateBoardResponseForm;
import com.example.monoproj.board.service.BoardService;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.board.service.response.ListBoardResponse;
import com.example.monoproj.board.service.response.ReadBoardResponse;
import com.example.monoproj.board.service.response.UpdateBoardResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController // ResponseEntity<>를 사용하지 않을 수 있음, 사용자 요청을 받아 처리하고 자동으로 json 형태로 응답
@RequiredArgsConstructor // spring에서 관리하는 bean - singleton
@RequestMapping("/board")
public class BoardController {
    final private BoardService boardService; // final 기반의 필드?
    final private RedisCacheService redisCacheService;

    @GetMapping("/list")
    public ListBoardResponseForm boardList(@ModelAttribute ListBoardRequestForm requestForm) {
        log.info("boardList() -> {}", requestForm);

        ListBoardResponse response = boardService.list(requestForm.toListBoardRequest());

        return ListBoardResponseForm.from(
                List.of(response),
                (int) response.getTotalItems(),
                response.getTotalPages()
        );
    }

    @PostMapping("/register")
    public CreateBoardResponseForm registerBoard (
            @RequestHeader("Authorization") String authorizationHeader, // frontend에서 보안 header에 ...
            @RequestBody CreateBoardRequestForm createBoardRequestForm) { // POST에서 JSON의 형태로 들어온 데이터를 수신할 때
        log.info("registerBoard() -> {}", createBoardRequestForm);
        log.info("authorizationHeader -> {}", authorizationHeader);

        String token = authorizationHeader.replace("Bearer ", "").trim();

        Long accountId = redisCacheService.getValueByKey(token);
        log.info("accountId -> {}", accountId);

        CreateBoardResponse response = boardService.register(createBoardRequestForm.toCreateBoardRequest(accountId));
        return CreateBoardResponseForm.from(response);
    }

    @GetMapping("/read/{boardId}")
    public ReadBoardResponseForm readBoard(@PathVariable("boardId") Long boardId) {
        log.info("boardRead(): {}", boardId);
        ReadBoardResponse response = boardService.read(boardId);
        return ReadBoardResponseForm.from(response);
    }

    @PutMapping("/update/{boardId}")
    public UpdateBoardResponseForm updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody UpdateBoardRequestForm updateBoardRequestForm,
            @RequestHeader("Authorization") String authorizationHeader) {

        log.info("modifyBoard(): {}, boardId: {}", updateBoardRequestForm, boardId);

        String token = authorizationHeader.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(token);
        log.info("accountId -> {}", accountId);

        UpdateBoardResponse response = boardService.update(
                boardId,
                accountId,
                updateBoardRequestForm.toUpdateBoardRequest()
        );

        return UpdateBoardResponseForm.from(response);
    }
}
