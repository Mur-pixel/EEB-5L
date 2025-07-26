package com.example.account.controller.request;

import com.example.account.entity.Account;
import com.example.account.utility.EncryptionUtility;

public class RegisterAccountRequest {
    private String userId;
    private String password;

    public RegisterAccountRequest() {
    }

    public RegisterAccountRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Account toAccount() {
        return new Account(userId, EncryptionUtility.encode(password));
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
