package com.example.monoproj.sampleBoard.controller.request_form;

import com.example.monoproj.board.service.request.UpdateBoardRequest;
import com.example.monoproj.sampleBoard.service.request.UpdateSampleBoardRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateSampleBoardRequestForm {
    final private String title;
    final private String content;
    final private Long accountId;

    public UpdateSampleBoardRequest toUpdateSampleBoardRequest() {
        return new UpdateSampleBoardRequest(title, content, accountId);
    }
}
