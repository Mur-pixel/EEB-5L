package com.example.monoproj.kakao_authentication.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.entity.LoginType;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.service.AccountProfileService;
import com.example.monoproj.config.FrontendConfig;
import com.example.monoproj.kakao_authentication.repository.KakaoAuthenticationRepository;
import com.example.monoproj.kakao_authentication.service.response.ExistingUserKakaoLoginResponse;
import com.example.monoproj.kakao_authentication.service.response.KakaoLoginResponse;
import com.example.monoproj.kakao_authentication.service.response.NewUserKakaoLoginResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoAuthenticationServiceImpl implements KakaoAuthenticationService {
//    final private KakaoAuthenticationRepository kakaoAuthenticationRepository;
//
//    @Override
//    public String getLoginLink() {
//        return this.kakaoAuthenticationRepository.getLoginLink();
//    }
//
//    @Override
//    public Map<String, Object> requestAccessToken(String code) {
//        return this.kakaoAuthenticationRepository.getAccessToken(code);
//    }
//
//    @Override
//    public Map<String, Object> requestUserInfo(String accessToken) {
//        return this.kakaoAuthenticationRepository.getUserInfo(accessToken);
//    }

    private final KakaoAuthenticationRepository kakaoAuthRepository;
    private final AccountProfileService accountProfileService;
    private final RedisCacheService redisCacheService;
    private final FrontendConfig frontendConfig;

    @Override
    public KakaoLoginResponse handleLogin(String code) {
        Map<String, Object> tokenResponse = kakaoAuthRepository.getAccessToken(code);
        String accessToken = (String) tokenResponse.get("access_token");

        Map<String, Object> userInfo = kakaoAuthRepository.getUserInfo(accessToken);
        String nickname = extractNickname(userInfo);
        String email = extractEmail(userInfo);

        String origin = frontendConfig.getOrigins().get(0);
        Optional<AccountProfile> optionalProfile =
                accountProfileService.loadProfileByEmailAndLoginType(email, LoginType.KAKAO);

        if (optionalProfile.isEmpty()) {
            String tempToken = createTemporaryUserToken(accessToken);
            return new NewUserKakaoLoginResponse(tempToken, nickname, email, origin);
        }

        Account account = optionalProfile.get().getAccount();
        String userToken = createUserTokenWithAccessToken(account, accessToken);
        return new ExistingUserKakaoLoginResponse(userToken, nickname, email, origin);
    }

    private String extractNickname(Map<String, Object> userInfo) {
        return (String) ((Map<?, ?>) userInfo.get("properties")).get("nickname");
    }

    private String extractEmail(Map<String, Object> userInfo) {
        return (String) ((Map<?, ?>) userInfo.get("kakao_account")).get("email");
    }

    private String createTemporaryUserToken(String accessToken) {
        String token = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(token, accessToken, Duration.ofMinutes(5));
        return token;
    }

    private String createUserTokenWithAccessToken(Account account, String accessToken) {
        String userToken = UUID.randomUUID().toString();
        redisCacheService.setKeyAndValue(account.getId(), accessToken);
        redisCacheService.setKeyAndValue(userToken, account.getId());
        return userToken;
    }
}
