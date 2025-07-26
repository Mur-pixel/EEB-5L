package com.example.monoproj.sampleBoard.service.response;

import com.example.monoproj.board.entity.Board;
import com.example.monoproj.sampleBoard.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadSampleBoardResponse {
    private final Long boardId;
    private final String title;
    private final String nickname; // AccountProfile의 nickname
    private final String content;
    private final LocalDateTime createDate;

    // 정적 팩토리 메서드
    public static ReadSampleBoardResponse from(SampleBoard board) {
        return new ReadSampleBoardResponse(
                board.getSampleBoardId(),
                board.getTitle(),
                board.getWriter().getNickname(),
                board.getContent(),
                board.getCreateDate()
        );
    }
}
