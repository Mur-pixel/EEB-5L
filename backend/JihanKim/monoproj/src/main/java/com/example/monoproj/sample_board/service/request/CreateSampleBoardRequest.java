package com.example.monoproj.sample_board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardRequest {
    private final String title;
    private final String content;
}
