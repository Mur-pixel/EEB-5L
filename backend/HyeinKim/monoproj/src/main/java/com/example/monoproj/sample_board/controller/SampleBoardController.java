package com.example.monoproj.sample_board.controller;

import com.example.monoproj.sample_board.controller.request_form.CreateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.service.SampleBoardService;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample_board")
public class SampleBoardController {
    final private SampleBoardService sampleBoardService;

    @PostMapping("/register")
    public CreateSampleBoardResponseForm registerBoard (@RequestBody CreateSampleBoardRequestForm createSampleBoardRequestForm) {
        log.info("registerBoard: {}", createSampleBoardRequestForm);

        CreateSampleBoardResponse response = sampleBoardService.register(createSampleBoardRequestForm.toCreateSampleBoardeRequest());
        return CreateSampleBoardResponseForm.from(response);

    }
}
