package com.example.monoproj.sampleBoard.service.response;

import com.example.monoproj.board.entity.Board;
import com.example.monoproj.sampleBoard.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSampleBoardResponse {
    private final Long boardId;
    private final String title;
    private final String nickname;
    private final String content;
    private final LocalDateTime createDate;

    public static UpdateSampleBoardResponse from(SampleBoard board) {
        return new UpdateSampleBoardResponse(
                board.getSampleBoardId(),
                board.getTitle(),
                board.getWriter().getNickname(),
                board.getContent(),
                board.getCreateDate()
        );
    }
}
