package com.example.monoproj.sample_board.controller.request_form;

import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ListSampleBoardRequestForm {

    final private int page;
    final private int perPage;

    public ListSampleBoardRequest toListSampleBoardRequest() {
        return new ListSampleBoardRequest(page, perPage);
    }
}
