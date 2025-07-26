package com.example.demo.sample_board.controller.request_form;

import com.example.demo.sample_board.service.request.CreateSampleBoardRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateSampleBoardRequestForm {
    private String title;
    private String content;

    public CreateSampleBoardRequest toCreateSampleBoardRequest() {
        return new CreateSampleBoardRequest(title, content);
    }
}
