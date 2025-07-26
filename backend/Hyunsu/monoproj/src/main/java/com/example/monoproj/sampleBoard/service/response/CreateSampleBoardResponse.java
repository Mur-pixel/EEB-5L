package com.example.monoproj.sampleBoard.service.response;

import com.example.monoproj.board.entity.Board;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.sampleBoard.entity.SampleBoard;
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

    public static CreateSampleBoardResponse from(SampleBoard sampleBoard) {
        return new CreateSampleBoardResponse(
                sampleBoard.getSampleBoardId(),
                sampleBoard.getTitle(),
                sampleBoard.getContent(),
                sampleBoard.getWriter().getNickname(),
                sampleBoard.getCreateDate()
        );
    }
}

