package com.example.monoproj.google_authentication.repository;

import java.util.Map;

public interface GoogleAuthenticationRepository {
    String getLoginLink();
    Map<String, Object> getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);
}
