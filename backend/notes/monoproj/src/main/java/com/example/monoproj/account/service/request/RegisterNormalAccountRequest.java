package com.example.monoproj.account.service.request;

import com.example.monoproj.account.entity.LoginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterNormalAccountRequest {
    private final LoginType loginType;
}
