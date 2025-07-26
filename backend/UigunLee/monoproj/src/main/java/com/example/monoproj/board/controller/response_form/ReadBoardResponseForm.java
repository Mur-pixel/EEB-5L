package com.example.monoproj.board.controller.response_form;

import com.example.monoproj.board.service.response.ReadBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReadBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createDate;

    public static ReadBoardResponseForm from(ReadBoardResponse response) {
        return new ReadBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getNickname(),
                response.getCreateDate()
        );
    }
}

