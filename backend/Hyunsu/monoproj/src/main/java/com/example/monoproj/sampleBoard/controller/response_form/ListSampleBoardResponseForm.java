package com.example.monoproj.sampleBoard.controller.response_form;


import com.example.monoproj.sampleBoard.service.response.ListSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListSampleBoardResponseForm {
    private final List<Map<String, Object>> boardList;

    public static ListSampleBoardResponseForm from(ListSampleBoardResponse listSampleBoardResponse) {
        return new ListSampleBoardResponseForm(listSampleBoardResponse.getBoardListWithNicknames());
    }

}
