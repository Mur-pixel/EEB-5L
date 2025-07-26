package com.example.monoproj.board.controller.request_form;

import com.example.monoproj.board.service.request.UpdateBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateBoardRequestForm {
    final private String title;
    final private String content;
    final private String userToken;

    public UpdateBoardRequest toUpdateBoardRequest() {
        return new UpdateBoardRequest(title, content);
    }
}
