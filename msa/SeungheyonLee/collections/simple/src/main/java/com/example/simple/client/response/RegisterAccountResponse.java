package com.example.simple.client.response;

public class RegisterAccountResponse {
    private Boolean isRegisterSuccess;

    public RegisterAccountResponse() {
    }

    public RegisterAccountResponse(Boolean isRegisterSuccess) {
        this.isRegisterSuccess = isRegisterSuccess;
    }

    public Boolean getRegisterSuccess() {
        return isRegisterSuccess;
    }

    public void setRegisterSuccess(Boolean registerSuccess) {
        isRegisterSuccess = registerSuccess;
    }
}
