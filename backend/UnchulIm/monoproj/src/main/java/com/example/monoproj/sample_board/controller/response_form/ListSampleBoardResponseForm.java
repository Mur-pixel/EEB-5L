package com.example.monoproj.sample_board.controller.response_form;

import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListSampleBoardResponseForm {
    private final List<Map<String, Object>> boardList;
    private final int totalItems;
    private final int totalPages;

    public static ListSampleBoardResponseForm from(List<ListSampleBoardResponse> responses, int totalItems, int totalPages) {
        List<Map<String, Object>> combinedList = responses.stream()
                .flatMap(response -> response.getSampleBoardList().stream())
                .collect(Collectors.toList());

        return new ListSampleBoardResponseForm(combinedList, totalItems, totalPages);
    }
}
