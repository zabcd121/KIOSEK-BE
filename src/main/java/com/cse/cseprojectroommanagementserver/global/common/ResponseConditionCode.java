package com.cse.cseprojectroommanagementserver.global.common;

import lombok.Getter;

@Getter
public enum ResponseConditionCode {
    /**
     * Auth: 01
     */
    LOGIN_SUCCESS("0100", "로그인 성공"),
    ID_NULL("0101", "아이디가 입력되지 않았습니다."),
    PW_NULL("0102", "패스워드가 입력되지 않았습니다."),
    ID_NOT_EXIST("0103", "존재하지 않는 아이디입니다."),
    PW_INVALID("0104", "잘못된 비밀번호를 입력하였습니다."),
    LOGOUT_SUCCESS("0105", "로그아웃 성공"),

    TOKEN_NOT_PASSED("0106", "인증 토큰이 전달되지 않았습니다."),
    TOKEN_NOT_BEARER("0107", "Bearer 토큰이 전달되지 않았습니다."),
    TOKEN_WRONG_TYPE("0108", "잘못된 타입의 인증 토큰이 전달되었습니다."),
    TOKEN_EXPIRED("0109", "만료된 토큰이 전달되었습니다."),
    TOKEN_UNSUPPORTED("0110", "지원되지 않는 토큰이 전달되었습니다."),
    ACCESS_DENIED("0111", "접근 거부");

    private final String code;
    private final String message;

    ResponseConditionCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
