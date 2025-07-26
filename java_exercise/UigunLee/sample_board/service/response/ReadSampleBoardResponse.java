package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadSampleBoardResponse {
    private final Long sampleBoardId;
    private final String title;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdDate;

    public static ReadSampleBoardResponse from(SampleBoard sampleBoard) {
        return new ReadSampleBoardResponse(
                sampleBoard.getSampleBoardId(),
                sampleBoard.getTitle(),
                sampleBoard.getNickname(),
                sampleBoard.getContent(),
                sampleBoard.getCreateDate()
        );
    }
}
