package com.example.monoproj.sample_board.service.response;


import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardResponse {
    private final Long sampleBoardId;
    private final String title;
    private final String content;
    private final String writerName;
    private final LocalDateTime createdDate;

    public static CreateSampleBoardResponse from(SampleBoard sampleBoard) {
        return new CreateSampleBoardResponse(
                sampleBoard.getSampleBoardId(),
                sampleBoard.getTitle(),
                sampleBoard.getContent(),
                sampleBoard.getNickname(),
                sampleBoard.getCreateDate()
        );
    }

}
