package com.example.monoproj.kakao_authentication.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Slf4j
@Repository
public class KakaoAuthenticationRepositoryImpl implements KakaoAuthenticationRepository {
    // 카카오 로그인 요청 URL을 저장.
    private final String loginUrl;
    // 클라이언트 ID를 저장.
    private final String clientId;
    // 리디렉션 URI를 저장.
    private final String redirectUri;
    // 토큰 요청 엔드포인트를 저장.
    private final String tokenRequestUri;
    // 사용자 정보 요청 엔드포인트를 저장.
    private final String userInfoRequestUri;

    // RestTemplate을 통한 HTTP 통신을 수행.
    private final RestTemplate restTemplate;

    /**
     * 애플리케이션 설정값과 RestTemplate을 주입받아 초기화.
     */
    public KakaoAuthenticationRepositoryImpl(
            @Value("${kakao.login-url}") String loginUrl,
            @Value("${kakao.client-id}") String clientId,
            @Value("${kakao.redirect-uri}") String redirectUri,
            @Value("${kakao.token-request-uri}") String tokenRequestUri,
            @Value("${kakao.user-info-request-uri}") String userInfoRequestUri,
            RestTemplate restTemplate
    ) {
        this.loginUrl = loginUrl;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.tokenRequestUri = tokenRequestUri;
        this.userInfoRequestUri = userInfoRequestUri;
        this.restTemplate = restTemplate;
    }

    /**
     * 카카오 OAuth2 로그인 링크를 생성하여 반환.
     */
    public String getLoginLink() {
        return String.format(
                "%s/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                loginUrl, clientId, redirectUri
        );
    }

    /**
     * 인가 코드를 사용해 액세스 토큰을 요청하고 응답을 반환.
     */
    public Map<String, Object> getAccessToken(String code) {
        // 요청 본문에 필요한 파라미터 설정.
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);
        formData.add("client_secret", "");

        // 헤더에 콘텐츠 타입을 설정.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP 요청 엔티티 생성.
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        // POST 요청으로 토큰 정보를 조회.
        ResponseEntity<Map> response = restTemplate.exchange(
                tokenRequestUri, HttpMethod.POST, entity, Map.class
        );

        return response.getBody();
    }

    /**
     * 액세스 토큰을 사용해 사용자 정보를 조회하고 반환.
     */
    @Override
    public Map<String, Object> getUserInfo(String accessToken) {
        // 헤더에 Bearer 토큰을 설정.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // HTTP 요청 엔티티 생성.
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // GET 요청으로 사용자 정보 조회.
        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoRequestUri, HttpMethod.GET, entity, Map.class
        );

        // 조회된 사용자 정보를 로깅.
        log.info("User Info: {}", response.getBody());

        return response.getBody();
    }
}
