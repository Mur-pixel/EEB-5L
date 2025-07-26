package com.example.monoproj.sample_board.controller;

import com.example.monoproj.sample_board.controller.request_form.CreateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.request_form.ListSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.request_form.UpdateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.ListSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.ReadSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.UpdateSampleBoardResponseForm;
import com.example.monoproj.sample_board.service.SampleBoardService;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample-board")
public class SampleBoardController {

    private final SampleBoardService sampleBoardService;

    @GetMapping("/list")
    public ListSampleBoardResponseForm list(@ModelAttribute ListSampleBoardRequestForm requestForm) {
        log.info("list() -> {}", requestForm);
        ListSampleBoardResponse response = sampleBoardService.list(requestForm.toListSampleBoardRequest());
        return ListSampleBoardResponseForm.from(response);
    }

    @PostMapping("/register")
    public CreateSampleBoardResponseForm register(@RequestBody CreateSampleBoardRequestForm requestForm) {
        log.info("register() -> {}", requestForm);
        CreateSampleBoardResponse response = sampleBoardService.register(requestForm.toCreateSampleBoardRequest());
        return CreateSampleBoardResponseForm.from(response);
    }

    @GetMapping("/read/{sampleBoardId}")
    public ReadSampleBoardResponseForm read(@PathVariable Long sampleBoardId) {
        log.info("read() -> {}", sampleBoardId);
        ReadSampleBoardResponse response = sampleBoardService.read(sampleBoardId);
        return ReadSampleBoardResponseForm.from(response);
    }

    @PutMapping("/update/{sampleBoardId}")
    public UpdateSampleBoardResponseForm update(
            @PathVariable Long sampleBoardId,
            @RequestBody UpdateSampleBoardRequestForm requestForm) {
        log.info("update() -> sampleBoardId: {}, request: {}", sampleBoardId, requestForm);
        UpdateSampleBoardResponse response = sampleBoardService.update(
                sampleBoardId,
                requestForm.getNickname(),
                requestForm.toUpdateSampleBoardRequest()
        );
        return UpdateSampleBoardResponseForm.from(response);
    }
}
