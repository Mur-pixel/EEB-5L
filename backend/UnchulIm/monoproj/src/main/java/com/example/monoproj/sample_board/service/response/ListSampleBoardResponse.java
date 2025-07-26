package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListSampleBoardResponse {
    private final List<SampleBoard> sampleBoardList;
    private final long totalItems;
    private final int totalPages;

    public List<Map<String, Object>> getSampleBoardList() {
        return sampleBoardList.stream().map(sampleBoard -> {
            Map<String, Object> map = new HashMap<>();
            map.put("boardId", sampleBoard.getBoardId());
            map.put("title", sampleBoard.getTitle());
            map.put("content", sampleBoard.getContent());
            map.put("createDate", formatDate(sampleBoard.getCreateDate()));
            return map;
        }).collect(Collectors.toList());
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
