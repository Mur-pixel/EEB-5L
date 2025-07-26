package com.example.monoproj.board.service.response;

import com.example.monoproj.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadBoardResponse {
    private final Long boardId;
    private final String title;
    private final String nickname; // AccountProfile의 nickname
    private final String content;
    private final LocalDateTime createDate;

    // 정적 팩토리 메서드
    public static ReadBoardResponse from(Board board) {
        return new ReadBoardResponse(
                board.getBoardId(),
                board.getTitle(),
                board.getWriter().getNickname(),
                board.getContent(),
                board.getCreateDate()
        );
    }
}
