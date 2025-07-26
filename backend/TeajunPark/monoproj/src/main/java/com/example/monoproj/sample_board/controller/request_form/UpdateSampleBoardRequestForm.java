package com.example.monoproj.sample_board.controller.request_form;

import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateSampleBoardRequestForm {
    final private String title;
    final private String content;
    final private String userToken;

    public UpdateSampleBoardRequest toUpdateSampleBoardRequest(){ return new UpdateSampleBoardRequest(title, content); }
}
