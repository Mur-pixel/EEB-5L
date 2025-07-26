package com.example.monoproj.sample_board.controller.request_form;

import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSampleBoardRequestForm {
    private String title;
    private String content;
    private String nickname; // 수정 권한 확인용

    public UpdateSampleBoardRequest toUpdateSampleBoardRequest() {
        return new UpdateSampleBoardRequest(title, content);
    }
}
