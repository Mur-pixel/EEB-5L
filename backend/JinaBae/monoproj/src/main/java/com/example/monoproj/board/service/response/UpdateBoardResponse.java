package com.example.monoproj.board.service.response;

import com.example.monoproj.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateBoardResponse {
    private final Long boardId;
    private final String title;
    private final String nickname; // AccountProfile의 nickname
    private final String content;
    private final LocalDateTime createDate;

    public static UpdateBoardResponse from(Board board) {
        return new UpdateBoardResponse(
                board.getBoardId(),
                board.getTitle(),
                board.getWriter().getNickname(),
                board.getContent(),
                board.getCreateDate()
        );
    }
}
