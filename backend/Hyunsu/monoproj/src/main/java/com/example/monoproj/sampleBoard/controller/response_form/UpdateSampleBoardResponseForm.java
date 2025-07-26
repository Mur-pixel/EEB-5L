package com.example.monoproj.sampleBoard.controller.response_form;

import com.example.monoproj.sampleBoard.service.response.UpdateSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSampleBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createDate;

    public static UpdateSampleBoardResponseForm from(UpdateSampleBoardResponse response) {
        return new UpdateSampleBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getNickname(),
                response.getCreateDate()
        );
    }
}

