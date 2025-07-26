package com.example.monoproj.kakao_authentication.controller;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.service.AccountService;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.service.AccountProfileService;
import com.example.monoproj.kakao_authentication.controller.request_form.AccessTokenRequestForm;
import com.example.monoproj.kakao_authentication.service.KakaoAuthenticationService;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao-authentication")
public class KakaoAuthenticationController {
    private final KakaoAuthenticationService kakaoAuthenticationService;
    private final AccountService accountService;
    private final AccountProfileService accountProfileService;
    private final RedisCacheService redisCacheService;

    /**
     * 로그인용 URL을 요청받아 반환한다.
     */
    @GetMapping("/request-login-url")
    public String requestGetLoginLink() {
        log.info("requestGetLoginLink() called");
        return kakaoAuthenticationService.getLoginLink();
    }

    /**
     * 인가 코드를 받아 토큰 발급과 사용자 정보 조회를 처리한다.
     */
    @GetMapping("/login")
    @Transactional
    public void requestAccessToken(@RequestParam("code") String code,
                                   HttpServletResponse response) throws IOException {
        log.info("requestAccessToken(): code {}", code);

        try {
            // 인가 코드를 통해 액세스 토큰 요청.
            Map<String, Object> tokenResponse = kakaoAuthenticationService.requestAccessToken(code);
            String accessToken = (String) tokenResponse.get("access_token");

            // 액세스 토큰으로 사용자 정보 요청.
            Map<String, Object> userInfo = kakaoAuthenticationService.requestUserInfo(accessToken);
            log.info("userInfo: {}", userInfo);

            // 사용자 닉네임과 이메일 추출.
            String nickname = (String) ((Map) userInfo.get("properties")).get("nickname");
            String email = (String) ((Map) userInfo.get("kakao_account")).get("email");
            log.info("email: {}", email);

            // 기존 프로필 조회.
            Optional<AccountProfile> optionalProfile = accountProfileService.loadProfileByEmail(email);
            Account account = null;

            if (optionalProfile.isPresent()) {
                // 기존 회원인 경우 계정 객체 할당.
                account = optionalProfile.get().getAccount();
                log.info("account (existing): {}", account);
            }

            if (account == null) {
                // 신규 회원인 경우 계정과 프로필 생성.
                log.info("New user detected. Creating account and profile...");
                account = accountService.createAccount();
                accountProfileService.createAccountProfile(account, nickname, email);
            }

            // Redis에 토큰과 계정 매핑 저장 후 사용자 토큰 생성.
            String userToken = createUserTokenWithAccessToken(account, accessToken);

            // 팝업 창에 전달할 HTML 스크립트 생성.
            String htmlResponse = """
            <html>
              <body>
                <script>
                  window.opener.postMessage({
                    accessToken: '%s',
                    user: { name: '%s', email: '%s' }
                  }, 'http://localhost');
                  window.close();
                </script>
              </body>
            </html>
            """.formatted(userToken, nickname, email);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(htmlResponse);

        } catch (Exception e) {
            // 오류 발생 시 에러 응답 전송.
            log.error("Kakao 로그인 에러", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "카카오 로그인 실패: " + e.getMessage());
        }
    }

    /**
     * Redis에 사용자 토큰과 액세스 토큰을 매핑하여 저장한다.
     */
    private String createUserTokenWithAccessToken(Account account, String accessToken) {
        try {
            String userToken = UUID.randomUUID().toString();
            redisCacheService.setKeyAndValue(account.getId(), accessToken);
            redisCacheService.setKeyAndValue(userToken, account.getId());
            return userToken;
        } catch (Exception e) {
            throw new RuntimeException("Error storing token in Redis: " + e.getMessage());
        }
    }
}
