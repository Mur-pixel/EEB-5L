package com.example.monoproj.sample_board.controller;

import com.example.monoproj.sample_board.controller.request_form.CreateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.service.SampleBoardService;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample-board")
public class SampleBoardController {
    final private SampleBoardService sampleBoardService;

    @PostMapping("/registerSample")
    public CreateSampleBoardResponseForm registerSampleBoard (
            @RequestBody CreateSampleBoardRequestForm createSampleBoardRequestForm) {
        log.info("registerSampleBoard() -> {}", createSampleBoardRequestForm);

        CreateSampleBoardResponse response = sampleBoardService.register(createSampleBoardRequestForm.toCreateSampleBoardRequest());
        return CreateSampleBoardResponseForm.from(response);
    }
}
