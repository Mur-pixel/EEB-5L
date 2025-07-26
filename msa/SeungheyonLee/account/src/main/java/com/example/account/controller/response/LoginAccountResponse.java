package com.example.account.controller.response;

import com.example.account.entity.Account;

public class LoginAccountResponse {
    private String userToken;

    public LoginAccountResponse() {
    }

    public LoginAccountResponse(String userToken) {
        this.userToken = userToken;
    }

    public static LoginAccountResponse from(String userToken) {
        return new LoginAccountResponse(userToken);
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
