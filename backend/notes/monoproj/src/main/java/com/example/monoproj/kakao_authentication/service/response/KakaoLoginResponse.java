package com.example.monoproj.kakao_authentication.service.response;

public abstract class KakaoLoginResponse {
    public abstract String getHtmlResponse();

    protected static String escape(String str) {
        return str.replace("'", "\\'");
    }
}
