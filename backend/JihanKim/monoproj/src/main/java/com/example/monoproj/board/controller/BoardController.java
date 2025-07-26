package com.example.monoproj.board.controller;

import com.example.monoproj.board.controller.request_form.CreateBoardRequestForm;
import com.example.monoproj.board.controller.request_form.ListBoardRequestForm;
import com.example.monoproj.board.controller.response_form.CreateBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ListBoardResponseForm;
import com.example.monoproj.board.service.BoardService;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.board.service.response.ListBoardResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    final private BoardService boardService;
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
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody CreateBoardRequestForm createBoardRequestForm) {
        log.info("registerBoard() -> {}", createBoardRequestForm);
        log.info("authorizationHeader -> {}", authorizationHeader);

        String token = authorizationHeader.replace("Bearer ", "").trim();

        Long accountId = redisCacheService.getValueByKey(token);
        log.info("accountId -> {}", accountId);

        CreateBoardResponse response = boardService.register(createBoardRequestForm.toCreateBoardRequest(accountId));
        return CreateBoardResponseForm.from(response);
    }
}
