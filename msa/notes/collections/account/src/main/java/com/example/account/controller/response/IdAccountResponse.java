package com.example.account.controller.response;

public class IdAccountResponse {
    private Long accountId;

    public IdAccountResponse() {
    }

    public IdAccountResponse(Long accountId) {
        this.accountId = accountId;
    }

    public static IdAccountResponse from(Long accountId) {
        return new IdAccountResponse(accountId);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
