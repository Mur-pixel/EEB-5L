package com.example.monoproj.sampleBoard.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateSampleBoardRequest {
    final private String title;
    final private String content;
    final private Long accountId;
}
