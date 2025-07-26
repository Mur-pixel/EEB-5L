package com.example.monoproj.sample_board.controller;

import com.example.monoproj.board.controller.request_form.CreateBoardRequestForm;
import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.ListSampleBoardResponseForm;
import com.example.monoproj.sample_board.service.SampleBoardService;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample_board")
public class SampleBoardController {

    final private SampleBoardService sampleBoardService;

    @PostMapping("/register")
    public CreateSampleBoardResponseForm registerBoard(
            @RequestBody CreateBoardRequestForm createBoardRequestForm){

        log.info("registerBoard -> {}", createBoardRequestForm);

        CreateSampleBoardResponse response = sampleBoardService
                .register(createBoardRequestForm.toCreateBoardRequest(accountId));

        return CreateSampleBoardResponseForm.from(response);
    }


    @GetMapping("/list")
    public ListSampleBoardResponseForm boardList(){

        ListSampleBoardResponse response = sampleBoardService.listSampleBoard();

        return new ListSampleBoardResponseForm(response.getBoardListWithNicknames());
    }
}
