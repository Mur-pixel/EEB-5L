package com.example.monoproj.board.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ListBoardRequest {
    final private int page;
    final private int perPage;
}
