package com.example.monoproj.kakao_authentication.repository;

import java.util.Map;

public interface KakaoAuthenticationRepository {
    String getLoginLink();
    Map<String, Object> getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);
}
