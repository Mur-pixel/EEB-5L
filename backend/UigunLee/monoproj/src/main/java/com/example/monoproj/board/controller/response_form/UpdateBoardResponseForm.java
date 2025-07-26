package com.example.monoproj.board.controller.response_form;

import com.example.monoproj.board.service.response.UpdateBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String nickname;
    private final LocalDateTime createDate;

    public static UpdateBoardResponseForm from(UpdateBoardResponse response) {
        return new UpdateBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getNickname(),
                response.getCreateDate()
        );
    }
}

