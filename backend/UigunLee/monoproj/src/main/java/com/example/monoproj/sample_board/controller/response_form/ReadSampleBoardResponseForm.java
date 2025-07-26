package com.example.monoproj.sample_board.controller.response_form;

import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadSampleBoardResponseForm {
    private final Long sampleBoardId;
    private final String title;
    private final String nickname;
    private final String content;
    private final LocalDateTime createDate;

    public static ReadSampleBoardResponseForm from(ReadSampleBoardResponse response) {
        return new ReadSampleBoardResponseForm(
                response.getSampleBoardId(),
                response.getTitle(),
                response.getNickname(),
                response.getContent(),
                response.getCreatedDate()
        );
    }
}
