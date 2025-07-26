package com.example.monoproj.board.controller.request_form;

import com.example.monoproj.board.service.request.CreateBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateBoardRequestForm {
    final private String title;
    final private String content;

    public CreateBoardRequest toCreateBoardRequest(Long accountId) {
        return new CreateBoardRequest(title, accountId, content);
    }
}
