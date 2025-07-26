package com.example.monoproj.board.controller.response_form;

import com.example.monoproj.board.entity.Board;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateBoardResponseForm {
    private final Long boardId;
    private final String title;
    private final String content;
    private final String writerNickname;
    private final LocalDateTime createDate;

    public static CreateBoardResponseForm from(CreateBoardResponse response) {
        return new CreateBoardResponseForm(
                response.getBoardId(),
                response.getTitle(),
                response.getContent(),
                response.getWriterNickname(),
                response.getCreateDate()
        );
    }
}

