package com.example.monoproj.board.service.response;

import com.example.monoproj.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateBoardResponse {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String writerNickname;
    private final LocalDateTime createDate;

    public static CreateBoardResponse from(Board board) {
        return new CreateBoardResponse(
                board.getBoardId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter().getNickname(),
                board.getCreateDate()
        );
    }
}

