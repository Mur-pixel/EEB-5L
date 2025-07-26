package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entitiy.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateSampleBoardResponse {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String writerNickname;
    private final LocalDateTime createDate;

    public static CreateSampleBoardResponse from(SampleBoard board) {
        return new CreateSampleBoardResponse(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter().getNickname(),
                board.getCreateDate()
        );
    }
}
