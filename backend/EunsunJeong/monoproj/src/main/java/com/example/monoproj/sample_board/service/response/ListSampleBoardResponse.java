package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entitiy.SampleBoard;
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


    //날짜 포맷
    public List<Map<String, Object>> getBoardListWithNicknames(){

        return sampleBoardList.stream().map(board -> {
            Map<String, Object> boardMap = new HashMap<>();
            boardMap.put("boardId", board.getBoardId());
            boardMap.put("title", board.getTitle());
            boardMap.put("content", board.getContent());
            boardMap.put("nickname", board.getWriter().getNickname());
            boardMap.put("date", formatDate(board.getCreateDate()));
            return boardMap;
        }).collect(Collectors.toList());
    }

    //날짜 포맷팅 함수
    private String formatDate(LocalDateTime dateTime){

        if(dateTime == null){
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dateTime.format(formatter);
    }
}
