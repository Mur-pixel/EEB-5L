package com.example.monoproj.sample_board.controller.request_form;

import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSampleBoardRequestForm {
    private String title;
    private String content;
    private String nickname;  // 닉네임 필드 추가

    public UpdateSampleBoardRequest toUpdateSampleBoardRequest() {
        return new UpdateSampleBoardRequest(title, content);
    }
}