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

    public List<Map<String, Object>> getSampleBoardListWithNickname(){
        return sampleBoardList.stream().map(board -> {
            Map<String, Object> boardMap = new HashMap<>();
            boardMap.put("boardId", board.getSample_board_id());
            boardMap.put("title", board.getSample_board_title());
            boardMap.put("content", board.getSample_board_content());
            boardMap.put("nickname", board.getSample_Board_writer().getNickname());
            boardMap.put("createDate", formatDate(board.getCreateDate()));
            return boardMap;
        }).collect(Collectors.toList());
    }

    private String formatDate(LocalDateTime dateTime){
        if(dateTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
