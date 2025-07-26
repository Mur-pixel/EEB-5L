package com.example.monoproj.sampleBoard.controller;

import com.example.monoproj.redis_cache.service.RedisCacheService;
import com.example.monoproj.sampleBoard.controller.request_form.CreateSampleBoardRequestForm;
import com.example.monoproj.sampleBoard.controller.request_form.UpdateSampleBoardRequestForm;
import com.example.monoproj.sampleBoard.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sampleBoard.controller.response_form.ListSampleBoardResponseForm;
import com.example.monoproj.sampleBoard.controller.response_form.ReadSampleBoardResponseForm;
import com.example.monoproj.sampleBoard.controller.response_form.UpdateSampleBoardResponseForm;
import com.example.monoproj.sampleBoard.service.SampleBoardService;
import com.example.monoproj.sampleBoard.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.ListSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sampleBoard.service.response.UpdateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample-board")
public class SampleBoardController {
    final private SampleBoardService sampleBoardService;
    final private RedisCacheService redisCacheService;

    @GetMapping("/list")
    public ListSampleBoardResponseForm boardList() {

        ListSampleBoardResponse response = sampleBoardService.getAll();

        return ListSampleBoardResponseForm.from(response);
    }

    @PostMapping("/save")
    public CreateSampleBoardResponseForm registerBoard (
            @RequestBody CreateSampleBoardRequestForm createSampleBoardRequestForm) {
        log.info("registerBoard() -> {}", createSampleBoardRequestForm);
        CreateSampleBoardResponse response = sampleBoardService.save(createSampleBoardRequestForm.toCreateBoardRequest());
        return CreateSampleBoardResponseForm.from(response);
    }

    @GetMapping("/read/{boardId}")
    public ReadSampleBoardResponseForm readBoard(@PathVariable("boardId") Long boardId) {
        log.info("boardRead(): {}", boardId);
        ReadSampleBoardResponse response = sampleBoardService.read(boardId);
        return ReadSampleBoardResponseForm.from(response);
    }

    @PutMapping("/update/{boardId}")
    public UpdateSampleBoardResponseForm updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody UpdateSampleBoardRequestForm updateBoardRequestForm) {

        log.info("modifyBoard(): {}, boardId: {}", updateBoardRequestForm, boardId);

        UpdateSampleBoardResponse response = sampleBoardService.update(
                boardId,
                updateBoardRequestForm.getAccountId(),
                updateBoardRequestForm.toUpdateSampleBoardRequest()
        );

        return UpdateSampleBoardResponseForm.from(response);
    }

}
