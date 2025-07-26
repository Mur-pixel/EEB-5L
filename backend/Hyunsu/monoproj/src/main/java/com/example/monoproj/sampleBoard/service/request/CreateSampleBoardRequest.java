package com.example.monoproj.sampleBoard.service.request;

import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.board.entity.Board;
import com.example.monoproj.sampleBoard.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateSampleBoardRequest {
    final private String title;
    final private Long accountId;
    final private String content;

    public SampleBoard toBoard(AccountProfile writer) {
        return new SampleBoard(title, writer, content);
    }
}
