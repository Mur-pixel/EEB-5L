package com.example.simple.client.request;

public class RegisterAccountRequest {
    private String userId;
    private String password;

    public RegisterAccountRequest() {
    }

    public RegisterAccountRequest(String userId, String password) {
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
