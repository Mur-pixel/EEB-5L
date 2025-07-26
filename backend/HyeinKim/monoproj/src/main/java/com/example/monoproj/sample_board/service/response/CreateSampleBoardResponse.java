package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardResponse {
    private final Long boardId;
    private final String title;
    private final String writer;
    private final String content;
    private final LocalDateTime createDate;

    public static CreateSampleBoardResponse from(SampleBoard sampleBoard) {
        return new CreateSampleBoardResponse(
                sampleBoard.getBoardId(),
                sampleBoard.getTitle(),
                sampleBoard.getWriter(),
                sampleBoard.getContent(),
                sampleBoard.getCreateDate()
        );
    }
}
