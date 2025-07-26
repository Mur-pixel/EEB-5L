package com.example.monoproj.board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateBoardRequest {
    final private String title;
    final private String content;
}
