package com.example.monoproj.sample_board.service.request;

import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSampleBoardRequest {
    private String title;
    private String nickname; // 사용자가 입력하는 닉네임
    private String content;

    public SampleBoard toSampleBoard() {
        return new SampleBoard(title, nickname, content);
    }
}
