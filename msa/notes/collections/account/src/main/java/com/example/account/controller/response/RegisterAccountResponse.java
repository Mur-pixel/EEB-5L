package com.example.account.controller.response;

import com.example.account.entity.Account;

public class RegisterAccountResponse {
    private Boolean isRegisterSuccess;

    public RegisterAccountResponse() {
    }

    public RegisterAccountResponse(Boolean isRegisterSuccess) {
        this.isRegisterSuccess = isRegisterSuccess;
    }

    public static RegisterAccountResponse from(Account account) {
        return new RegisterAccountResponse(account != null);
    }

    public Boolean getRegisterSuccess() {
        return isRegisterSuccess;
    }

    public void setRegisterSuccess(Boolean registerSuccess) {
        isRegisterSuccess = registerSuccess;
    }
}
