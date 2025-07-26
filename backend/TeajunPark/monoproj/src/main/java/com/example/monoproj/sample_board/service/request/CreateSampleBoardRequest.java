package com.example.monoproj.sample_board.service.request;

import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.sample_board.entity.SampleBoard;
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

    public SampleBoard toSampleBoard(AccountProfile writer) {
        return new SampleBoard(title, writer, content);
    }
}
