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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class KakaoAuthenticationRepositoryImpl implements KakaoAuthenticationRepository {
    private final String loginUrl;
    private final String clientId;
    private final String redirectUri;
    private final String tokenRequestUri;
    private final String userInfoRequestUri;

    private final RestTemplate restTemplate;

    // 아래 파트를 보면 clientId, redirectUri, tokenRequestUri 등등의 정보를 가지고 있는 것을 볼 수 있습니다.
    // 이 정보들은 `.env` 에 적혀 있는 내용을 `application.yaml` 에서 인식해서
    // @Value Annotation을 통해 현재 위치에서 인식하고 있습니다.
    // kakao:
    //  login-url: ${KAKAO_LOGIN_URL}
    //  client-id: ${KAKAO_CLIENT_ID}

    // 그래서 실제 아래쪽의 KAKAO_CLIENT_ID를 눌러보면 kakao.client-id로 나타나는 것을 볼 수 있습니다.
    // KAKAO_CLIENT_ID는 `.env` 에 지정되어 있던 정보에 해당합니다.
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

    public String getLoginLink() {
        System.out.println("getLoginLink() for Login");
        return String.format("%s/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                loginUrl, clientId, redirectUri);
    }

    // authorize에 의해 KAKAO 벤더로부터 수신한 인증 code를 가지고 access token을 요청하는 과정입니다.
    public Map<String, Object> getAccessToken(String code) {
        // MultiValueMap <- Key, Value를 여러 다발로 받기 위해 사용합니다.
        // Kakao 벤더에서 요구하는 형식에 맞추기 위한 형식 데이터를 구성함
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token <- 형식
        // 위 링크에서 본문(Body)에 해당하는 내용을 맞춰 보내도록 구성하는 작업
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);
        formData.add("client_secret", "");

        // 헤더를 구성
        // 위 링크에서 `요청 헤더` 에 해당하는 부분이고
        // Content-Type: application/x-www-form-urlencoded 으로 되어 있음.
        // 이것은 Spring에서 `MediaType.APPLICATION_FORM_URLENCODED` 로 표현됨
        // Post 방식으로 전송하겠습니다라는 의미를 내포하고 있습니다.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HttpEntity의 경우 HTTP 요청 헤더 + 본문(Body)가 함께 구성되어야 합니다.
        // 위에서 Body를 만들었고 그 하단에 요청 헤더가 있으니 그냥 두 개를 가지고 HttpEntity를 구성
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);

        // restTemplate을 사용하는 순간은 Spring이 Client로서 외부 서버에게 요청하는 상황입니다.
        // restTemplate.exchange()는 HTTP 요청을 보내고 응답을 받는 매서드입니다.
        // tokenRequestUri는 Kakao에게 토큰을 요청할 때 사용하는 URI 주소
        // Kakao 벤더에서 반드시 POST로 요청하라고 명시하였음
        // 그리고 위에 만들어놓은 HttpEntity를 쏘아보냄
        // 4번째 인자는 결과를 Map 형태의 Key, Value로 받겠다는 의미입니다.
        ResponseEntity<Map> response = restTemplate.exchange(
                tokenRequestUri, HttpMethod.POST, entity, Map.class);

        // Header 등등 관심 없으니 응답 Body만 주세요.
        return response.getBody();
    }

    @Override
    public Map<String, Object> getUserInfo(String accessToken) {
        // GET/POST	https://kapi.kakao.com/v2/user/me
        // 요청: 액세스 토큰 방식 헤더 파트를 보면
        // Key로 "Authorization" 을 어떻게 구성해야 하는지 나옵니다.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoRequestUri, HttpMethod.GET, entity, Map.class);

        log.info("User Info: {}", response.getBody());

        return response.getBody();
    }
}

