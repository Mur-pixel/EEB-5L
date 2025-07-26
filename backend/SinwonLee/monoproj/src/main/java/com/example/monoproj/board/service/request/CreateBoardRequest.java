package com.example.monoproj.board.service.request;

import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.board.entity.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CreateBoardRequest {
    final private String title;
    final private Long accountId;
    final private String content;

    public Board toBoard(AccountProfile writer) {
        return new Board(title, writer, content);
    }
}
