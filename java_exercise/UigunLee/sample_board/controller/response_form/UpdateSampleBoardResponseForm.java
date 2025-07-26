package com.example.monoproj.sample_board.controller.response_form;

import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSampleBoardResponseForm {
    private final Long sampleBoardId;
    private final String title;
    private final String nickname;
    private final String content;
    private final LocalDateTime createDate;

    public static UpdateSampleBoardResponseForm from(UpdateSampleBoardResponse response) {
        return new UpdateSampleBoardResponseForm(
                response.getSampleBoardId(),
                response.getTitle(),
                response.getNickname(),
                response.getContent(),
                response.getCreateDate()
        );
    }
}
