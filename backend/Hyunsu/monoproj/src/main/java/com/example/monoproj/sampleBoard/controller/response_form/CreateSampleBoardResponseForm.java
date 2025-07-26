package com.example.monoproj.sampleBoard.controller.response_form;

import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.sampleBoard.service.response.CreateSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String writerNickname;
    private final LocalDateTime createDate;

    public static CreateSampleBoardResponseForm from(CreateSampleBoardResponse response) {
        return new CreateSampleBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getWriterNickname(),
                response.getCreateDate()
        );
    }
}

