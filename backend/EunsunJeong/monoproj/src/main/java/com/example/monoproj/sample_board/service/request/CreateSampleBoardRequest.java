package com.example.monoproj.sample_board.service.request;

import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.sample_board.entitiy.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateSampleBoardRequest {

    private final String title;
    private final Long accountId;
    private final String content;

    public SampleBoard toSampleBoard(AccountProfile writer){
        return new SampleBoard(title, writer, content);
    }
}