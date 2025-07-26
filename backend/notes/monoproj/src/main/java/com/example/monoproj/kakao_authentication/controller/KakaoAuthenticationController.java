package com.example.monoproj.kakao_authentication.controller;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.entity.LoginType;
import com.example.monoproj.account.service.AccountService;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.service.AccountProfileService;
import com.example.monoproj.config.FrontendConfig;
import com.example.monoproj.kakao_authentication.controller.request_form.AccessTokenRequestForm;
import com.example.monoproj.kakao_authentication.service.KakaoAuthenticationService;
import com.example.monoproj.kakao_authentication.service.response.KakaoLoginResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao-authentication")
public class KakaoAuthenticationController {
    final private KakaoAuthenticationService kakaoAuthenticationService;
    final private AccountService accountService;
    final private AccountProfileService accountProfileService;
    final private RedisCacheService redisCacheService;
    final private FrontendConfig frontendConfig;

//    @GetMapping("/request-login-url")
//    public String requestGetLoginLink() {
//        log.info("requestGetLoginLink() called");
//
//        return kakaoAuthenticationService.getLoginLink();
//    }

    // 실제로 Authentication(인증)은 Frontend에서 .env 파트가 담당하고 있습니다.
    // 해당 파트에서는 각 벤더(회사)별 authroize를 요청하게 됩니다.
    // authorize가 요청되면 각 벤더(회사)별 로그인 창이 나타나게 됩니다.
    // 여기서 여러분들이 id, password 를 입력하고 로그인을 하면
    // 그 다음으로 redirection이 발생하게 됩니다.
    // 그런데 .env에 작성해놓은 것을 보면
    // redirection이 현재 Backend의 /xxx-authentication/login 으로 요청이 날아갑니다.
    // 그래서 실제 Frontend에서 .env 파트로 요청 보내는 코드가 동작한 다음 로그인하면 아래 파트가 실행됩니다.
    
    // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
    // 1 ~ 6번에 해당하는 내용
//    @GetMapping("/login")
//    @Transactional
//    public void requestAccessToken(@RequestParam("code") String code,
//                                   HttpServletResponse response) throws IOException {
//
//        log.info("requestAccessToken(): code {}", code);
//
//        try {
//            // Step2 토큰 받기 1번 2번
//            // 사실 사용자 정보 수집이 필요 없는 서비스라면 그냥 여기 두 줄로 끝내도 무방합니다.
//            Map<String, Object> tokenResponse = kakaoAuthenticationService.requestAccessToken(code);
//            String accessToken = (String) tokenResponse.get("access_token");
//
//            // 별개로 사용자 정보를 확인하는 작업이 진행됨
//            // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
//            // 사용자 정보를 수집하기 위한 목적으로 사용되기에 서비스 구성에 따라 사용하지 않을 수도 있음.
//            Map<String, Object> userInfo = kakaoAuthenticationService.requestUserInfo(accessToken);
//            log.info("userInfo: {}", userInfo);
//
//            String nickname = (String) ((Map) userInfo.get("properties")).get("nickname");
//            String email = (String) ((Map) userInfo.get("kakao_account")).get("email");
//            log.info("email: {}", email);
//
//            // 로그인 타입과 이메일의 일치 여부를 확인해서 가입한 사용자인지 여부를 판정
//            Optional<AccountProfile> optionalProfile = accountProfileService.loadProfileByEmailAndLoginType(email, LoginType.KAKAO);
//            Account account = null;
//
//            // 기존 사용자
//            if (optionalProfile.isPresent()) {
//                account = optionalProfile.get().getAccount();
//                log.info("account (existing): {}", account);
//            }
//
//            String origin = frontendConfig.getOrigins().get(0);
//
//            // 신규 사용자
//            if (account == null) {
//                log.info("New user detected. Creating account and profile...");
//                String temporaryUserToken = createTemporaryUserToken(accessToken);
//
//                String htmlResponse = """
//                <html>
//                  <body>
//                    <script>
//                      window.opener.postMessage({
//                        newUser: true,
//                        loginType: 'KAKAO',
//                        temporaryUserToken: '%s',
//                        user: { name: '%s', email: '%s' }
//                      }, '%s');
//                      window.close();
//                    </script>
//                  </body>
//                </html>
//                """.formatted(temporaryUserToken, nickname, email, origin);
//
//                response.setContentType("text/html;charset=UTF-8");
//                response.getWriter().write(htmlResponse);
//                return;
//            }
//
//            String userToken = createUserTokenWithAccessToken(account, accessToken);
//            String htmlResponse = """
//            <html>
//              <body>
//                <script>
//                  window.opener.postMessage({
//                    accessToken: '%s',
//                    user: { name: '%s', email: '%s' }
//                  }, '%s');
//                  window.close();
//                </script>
//              </body>
//            </html>
//            """.formatted(userToken, nickname, email, origin);
//
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write(htmlResponse);
//
//        } catch (Exception e) {
//            log.error("Kakao 로그인 에러", e);
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "카카오 로그인 실패: " + e.getMessage());
//        }
//    }

    @GetMapping("/login")
    @Transactional
    public void requestAccessToken(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        log.info("requestAccessToken(): code {}", code);
        try {
            KakaoLoginResponse loginResponse = kakaoAuthenticationService.handleLogin(code);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(loginResponse.getHtmlResponse());
        } catch (Exception e) {
            log.error("Kakao 로그인 에러", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "카카오 로그인 실패: " + e.getMessage());
        }
    }

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

    private String createTemporaryUserToken(String accessToken) {
        try {
            String userToken = UUID.randomUUID().toString();
            redisCacheService.setKeyAndValue(userToken, accessToken, Duration.ofMinutes(5));
            return userToken;
        } catch (Exception e) {
            throw new RuntimeException("Error storing token in Redis: " + e.getMessage());
        }
    }
}
