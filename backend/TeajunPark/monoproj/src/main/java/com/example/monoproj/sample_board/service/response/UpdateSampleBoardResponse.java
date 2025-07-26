package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSampleBoardResponse {
    private final Long boardId;
    private final String title;
    private final String nickname; // AccountProfile의 nickname
    private final String content;
    private final LocalDateTime createDate;

    public static UpdateSampleBoardResponse from(SampleBoard sampleBoard) {
        return new UpdateSampleBoardResponse(
                sampleBoard.getSample_board_id(),
                sampleBoard.getSample_board_title(),
                sampleBoard.getSample_Board_writer().getNickname(),
                sampleBoard.getSample_board_content(),
                sampleBoard.getCreateDate()
        );
    }
}