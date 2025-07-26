package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSampleBoardResponse {
    private final Long boardId;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;

    public static UpdateSampleBoardResponse from(SampleBoard sampleBoard) {
        return new UpdateSampleBoardResponse(
                sampleBoard.getBoardId(),
                sampleBoard.getTitle(),
                sampleBoard.getContent(),
                sampleBoard.getCreateDate()
        );
    }
}
