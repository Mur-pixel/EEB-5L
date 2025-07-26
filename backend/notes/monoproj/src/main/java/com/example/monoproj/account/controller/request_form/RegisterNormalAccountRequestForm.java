package com.example.monoproj.account.controller.request_form;

import com.example.monoproj.account.entity.LoginType;
import com.example.monoproj.account.service.request.RegisterNormalAccountRequest;
import com.example.monoproj.account_profile.service.request.RegisterAccountProfileRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterNormalAccountRequestForm {
    final private String email;
    final private String nickname;
    final private LoginType loginType;
    final private String temporaryUserToken;

    public RegisterNormalAccountRequest toRegisterNormalAccountRequest() {
        return new RegisterNormalAccountRequest(loginType);
    }

    public RegisterAccountProfileRequest toRegisterAccountProfileRequest() {
        return new RegisterAccountProfileRequest(email, nickname);
    }
}
