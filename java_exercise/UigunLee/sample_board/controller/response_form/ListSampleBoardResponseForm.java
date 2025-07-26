package com.example.monoproj.sample_board.controller.response_form;

import com.example.monoproj.sample_board.entity.SampleBoard;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ListSampleBoardResponseForm {
    private final List<SampleBoard> sampleBoardList;
    private final int totalItems;
    private final int totalPages;

    public static ListSampleBoardResponseForm from(ListSampleBoardResponse response) {
        return new ListSampleBoardResponseForm(
                response.getSampleBoardList(),
                (int) response.getTotalItems(),
                response.getTotalPages()
        );
    }
}
