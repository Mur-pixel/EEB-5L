package com.example.monoproj.kakao_authentication.service;

import java.util.Map;

public interface KakaoAuthenticationService {
    String getLoginLink();
    Map<String, Object> requestAccessToken(String code);
    Map<String, Object> requestUserInfo(String accessToken);
}
