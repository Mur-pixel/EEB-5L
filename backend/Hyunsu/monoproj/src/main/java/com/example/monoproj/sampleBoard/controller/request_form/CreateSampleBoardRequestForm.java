package com.example.monoproj.sampleBoard.controller.request_form;

import com.example.monoproj.sampleBoard.service.request.CreateSampleBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateSampleBoardRequestForm {

    final private Long accountId;
    final private String title;
    final private String content;

    public CreateSampleBoardRequest toCreateBoardRequest() {
        return new CreateSampleBoardRequest(title, accountId, content);
    }
}
