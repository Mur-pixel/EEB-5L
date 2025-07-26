package com.example.monoproj.sampleBoard.controller.response_form;

import com.example.monoproj.sampleBoard.service.response.ReadSampleBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadSampleBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createDate;

    public static ReadSampleBoardResponseForm from(ReadSampleBoardResponse response) {
        return new ReadSampleBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getNickname(),
                response.getCreateDate()
        );
    }
}

