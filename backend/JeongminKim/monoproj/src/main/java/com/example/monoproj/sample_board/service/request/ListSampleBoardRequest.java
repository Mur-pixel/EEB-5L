package com.example.monoproj.sample_board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ListSampleBoardRequest {
    final private int page;
    final private int perPage;
}
