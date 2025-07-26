package com.example.monoproj.google_authentication.service;

import com.example.monoproj.google_authentication.repository.GoogleAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleAuthenticationServiceImpl implements GoogleAuthenticationService {
    final private GoogleAuthenticationRepository googleAuthenticationRepository;

    @Override
    public String getLoginLink() {
        return this.googleAuthenticationRepository.getLoginLink();
    }

    @Override
    public Map<String, Object> requestAccessToken(String code) {
        return this.googleAuthenticationRepository.getAccessToken(code);
    }

    @Override
    public Map<String, Object> requestUserInfo(String accessToken) {
        return this.googleAuthenticationRepository.getUserInfo(accessToken);
    }
}
