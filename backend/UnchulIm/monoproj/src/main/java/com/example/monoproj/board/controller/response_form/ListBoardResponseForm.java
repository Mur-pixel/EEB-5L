package com.example.monoproj.board.controller.response_form;

import com.example.monoproj.board.service.response.ListBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListBoardResponseForm {
    private final List<Map<String, Object>> boardList;  // 게시글 리스트
    private final int totalItems;  // 전체 아이템 수
    private final int totalPages;  // 전체 페이지 수

    // from 메서드를 추가하여 ListBoardResponseForm을 생성
    public static ListBoardResponseForm from(List<ListBoardResponse> boardListResponses, int totalItems, int totalPages) {
        // 모든 ListBoardResponse 객체의 boardListWithNicknames 값을 하나로 결합
        List<Map<String, Object>> combinedBoardList = boardListResponses.stream() // [0]
                .flatMap(response -> response.getBoardListWithNicknames().stream())  // 각 ListBoardResponse의 getBoardListWithNicknames 호출
                .collect(Collectors.toList());

        return new ListBoardResponseForm(combinedBoardList, totalItems, totalPages);
    }
}
