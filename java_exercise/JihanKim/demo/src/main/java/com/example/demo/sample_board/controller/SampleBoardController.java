package com.example.demo.sample_board.controller;

import com.example.demo.sample_board.controller.request_form.CreateSampleBoardRequestForm;
import com.example.demo.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.demo.sample_board.service.SampleBoardService;
import com.example.demo.sample_board.service.request.CreateSampleBoardRequest;
import com.example.demo.sample_board.service.response.CreateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample-board")
@RequiredArgsConstructor
public class SampleBoardController {
    private final SampleBoardService sampleBoardService;

    @PostMapping("/register")
    public ResponseEntity<CreateSampleBoardResponseForm> createSampleBoard(@RequestBody CreateSampleBoardRequestForm requestForm) {
        CreateSampleBoardRequest request = requestForm.toCreateSampleBoardRequest();
        CreateSampleBoardResponse response = sampleBoardService.createSampleBoard(request);
        CreateSampleBoardResponseForm responseForm = CreateSampleBoardResponseForm.from(response);

        return ResponseEntity.ok(responseForm);
    }
}
