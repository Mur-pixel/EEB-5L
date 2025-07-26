package com.example.monoproj.sample_board.controller;

import com.example.monoproj.redis_cache.service.RedisCacheService;
import com.example.monoproj.sample_board.controller.request_form.CreateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.ListSampleBoardResponseForm;
import com.example.monoproj.sample_board.service.SampleBoardService;
import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample_board")
public class SampleBoardController {
    final private SampleBoardService sampleBoardService;
    private final RedisCacheService redisCacheService;

    @GetMapping("/list")
    public ListSampleBoardResponseForm sampleBoardList() {

        ListSampleBoardResponse response = sampleBoardService.list();

        return ListSampleBoardResponseForm.from(
                List.of(response),
                (int) response.getTotalItems(),
                (int) response.getTotalPages()
        );
    }

    @PostMapping("/register")
    public CreateSampleBoardResponseForm registerSampleBoard(
            @RequestBody CreateSampleBoardRequestForm requestForm) {

        log.info("Register sample board: {}", requestForm);

        CreateSampleBoardResponse response = sampleBoardService.register(
                requestForm.toCreateSampleBoardRequest()
        );

        return CreateSampleBoardResponseForm.from(response);
    }

}
