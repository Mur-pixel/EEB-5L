package com.example.book.controller.response;

import com.example.book.entity.Book;
import lombok.Getter;

@Getter
public class IdAccountResponse {
    private Long accountId;

    public IdAccountResponse() {
    }

    public IdAccountResponse(Long accountId) {
        this.accountId = accountId;
    }
}
