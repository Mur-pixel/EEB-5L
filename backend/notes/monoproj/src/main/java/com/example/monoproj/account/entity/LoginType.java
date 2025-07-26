package com.example.monoproj.account.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum LoginType {
    KAKAO,
    GOOGLE,
    NAVER,
    GITHUB,
    META;

    @JsonCreator
    public static LoginType from(String input) {
        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown loginType: " + input));
    }
}

