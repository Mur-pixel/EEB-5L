package com.example.monoproj.sample_board.service.request;


import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateSampleBoardRequest {
    private final String title;
    private final String content;

    public SampleBoard toSampleBoard() {
        return new SampleBoard(title, content);
    }
}
