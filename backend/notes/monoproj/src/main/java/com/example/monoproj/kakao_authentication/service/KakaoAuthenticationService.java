package com.example.monoproj.kakao_authentication.service;

import com.example.monoproj.kakao_authentication.service.response.KakaoLoginResponse;

import java.util.Map;

//public interface KakaoAuthenticationService {
//    String getLoginLink();
//    Map<String, Object> requestAccessToken(String code);
//    Map<String, Object> requestUserInfo(String accessToken);
//}

public interface KakaoAuthenticationService {
    KakaoLoginResponse handleLogin(String code);
}