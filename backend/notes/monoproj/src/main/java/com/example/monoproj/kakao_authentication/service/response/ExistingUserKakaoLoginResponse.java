package com.example.monoproj.kakao_authentication.service.response;

public class ExistingUserKakaoLoginResponse extends KakaoLoginResponse {
    private final String htmlResponse;

    public ExistingUserKakaoLoginResponse(String token, String nickname, String email, String origin) {
        this.htmlResponse = """
        <html><body><script>
        window.opener.postMessage({
            accessToken: '%s',
            user: { name: '%s', email: '%s' }
        }, '%s'); window.close();
        </script></body></html>
        """.formatted(token, escape(nickname), escape(email), origin);
    }

    @Override
    public String getHtmlResponse() {
        return htmlResponse;
    }
}
