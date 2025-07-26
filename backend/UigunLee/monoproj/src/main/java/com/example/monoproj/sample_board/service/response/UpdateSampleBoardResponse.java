package com.example.monoproj.sample_board.service.response;

import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSampleBoardResponse {
    private final Long sampleBoardId;
    private final String title;
    private final String writerNickname;  // ✅ 명칭 일관화
    private final String content;
    private final LocalDateTime createDate;

    public static UpdateSampleBoardResponse from(SampleBoard board) {
        return new UpdateSampleBoardResponse(
                board.getSampleBoardId(),
                board.getTitle(),
                board.getNickname(),
                board.getContent(),
                board.getCreateDate()
        );
    }
}