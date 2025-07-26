package com.example.monoproj.sample_board.controller.response_form;

import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final LocalDateTime createDate;

    public static CreateSampleBoardResponseForm from(CreateSampleBoardResponse response) {
        return new CreateSampleBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getCreateDate()
        );
    }
}

