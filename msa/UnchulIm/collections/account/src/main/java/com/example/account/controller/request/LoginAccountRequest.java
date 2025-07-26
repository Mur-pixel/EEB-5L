package com.example.account.controller.request;

import com.example.account.entity.Account;

public class LoginAccountRequest {
    private String userId;
    private String password;

    public LoginAccountRequest() {
    }

    public LoginAccountRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
