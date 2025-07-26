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
    private final long totalPages;

    public List<Map<String, Object>> getSampleBoardListWithNicknames() {
        return sampleBoardList.stream().map(sampleBoard -> {
          Map<String, Object> sampleBoardMap = new HashMap<>();
          sampleBoardMap.put("sampleBoardId", sampleBoard.getSampleBoardId());
          sampleBoardMap.put("title", sampleBoard.getTitle());
          sampleBoardMap.put("content", sampleBoard.getContent());
          sampleBoardMap.put("nickname", sampleBoard.getWriter());
          sampleBoardMap.put("createDate", sampleBoard.getCreatedDate());
          return sampleBoardMap;
        }).collect(Collectors.toList());
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
