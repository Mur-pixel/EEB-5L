package com.example.demo.sample_board.service.response;

import com.example.demo.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;

    public static CreateSampleBoardResponse from(SampleBoard sampleBoard) {
        return new CreateSampleBoardResponse(sampleBoard.getId(), sampleBoard.getTitle(), sampleBoard.getContent(), sampleBoard.getCreateDate());
    }
}
