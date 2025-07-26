package com.example.monoproj.sample_board.controller.response_form;

import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ListSampleBoardResponseForm {
    private final List<Map<String, Object>> boardList;
}
