package com.example.monoproj.google_authentication.service;

import java.util.Map;

public interface GoogleAuthenticationService {
    String getLoginLink();
    Map<String, Object> requestAccessToken(String code);
    Map<String, Object> requestUserInfo(String accessToken);
}