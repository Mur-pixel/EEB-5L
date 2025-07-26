package com.example.monoproj.sample_board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateSampleBoardRequest {
    final private String title;
    final private String content;
}
