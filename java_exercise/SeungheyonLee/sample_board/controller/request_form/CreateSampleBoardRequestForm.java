package com.example.monoproj.sample_board.controller.request_form;

import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateSampleBoardRequestForm {
    private String title;
    private String content;
    private String nickname;

    public CreateSampleBoardRequest toCreateBoardRequest() {
        return new CreateSampleBoardRequest(
                title,
                content,
                (nickname != null && !nickname.isBlank()) ? nickname : "사용자"
        );
    }
}
