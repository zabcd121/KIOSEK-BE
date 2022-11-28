package com.cse.cseprojectroommanagementserver.global.common;

import lombok.Getter;

@Getter
public enum ResponseConditionCode {
    /**
     * Signup: 01
     */
    SIGNUP_SUCCESS("0100", "회원가입 성공"),
    SIGNUP_FAIL("0101", "회원가입 실패"),
    LOGIN_ID_DUPLICATED("0102", "이미 사용중인 아이디(학번) 입니다.)"),
    LOGIN_ID_NOT_DUPLICATED("0103", "사용 가능한 아이디 입니다."),
    EMAIL_DUPLICATED("0104", "이미 사용중인 이메일 입니다."),
    EMAIL_USABLE("0105", "사용 가능한 이메일 입니다."),
    LOGIN_ID_WRONG_TYPE("0106", "잘못된 형식의 아이디(학번)이 입력되었습니다."),
    PASSWORD_WRONG_TYPE("0107", "잘못된 형식의 비밃번호가 입력되었습니다. 최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다."),
    EMAIL_WRONG_TYPE("0108", "잘못된 형식의 이메일이 입력되었습니다."),
    EMAIL_AUTH_FAIL("0109", "이메일 인증 실패"),
    EMAIL_NULL("0110", "이메일이 입력되지 않았습니다."),
    NAME_WRONG_TYPE("0111", "이름 형식이 잘못 입력되었습니다."),
    NAME_NULL("0112", "이름이 입력되지 않았습니다."),
    ACCOUNT_QR_CREATE_FAIL("0113", "QR 코드 생성에 실패했습니다."),



    /**
     * Auth: 02
     */
    LOGIN_SUCCESS("0200", "로그인 성공"),
    LOGIN_ID_NULL("0201", "아이디가 입력되지 않았습니다."),
    PASSWORD_NULL("0202", "패스워드가 입력되지 않았습니다."),
    LOGIN_ID_NOT_EXIST("0203", "존재하지 않는 아이디입니다."),
    PASSWORD_INVALID("0204", "잘못된 비밀번호를 입력하였습니다."),
    LOGOUT_SUCCESS("0205", "로그아웃 성공"),

    TOKEN_NOT_PASSED("0206", "인증 토큰이 전달되지 않았습니다."),
    TOKEN_NOT_BEARER("0207", "Bearer 토큰이 전달되지 않았습니다."),
    TOKEN_WRONG_TYPE("0208", "잘못된 타입의 인증 토큰이 전달되었습니다."),
    TOKEN_WRONG_SIGNATURE("0209", "잘못된 서명이 전달되었습니다."),
    TOKEN_EXPIRED("0210", "만료된 토큰이 전달되었습니다."),
    TOKEN_UNSUPPORTED("0211", "지원되지 않는 토큰이 전달되었습니다."),
    ACCESS_DENIED("0212", "접근 거부"),
    REFRESH_TOKEN_NOT_EXIST_IN_STORE("0213", "refresh 토큰이 저장소에 존재하지 않습니다."),
    TOKEN_REISSUED("0214", "토큰이 재발급 되었습니다."),

    /**
     * Reservation: 03
     */
    RESERVATION_SUCCESS("0200", "예약 성공"),
    RESERVATION_FAIL_PENALTY_USER("0201", "계정 정지 상태이므로 예약이 불가능합니다."),
    RESERVATION_FAIL_RESERVATION_DUPLICATED("0202", "중복된 예약이 존재합니다."),
    RESERVATION_FAIL_EXCEED_MAX_TIME("0203", "예약가능 최대시간을 초과하였습니다."),
    RESERVATION_FAIL_EXCEED_MAX_COUNT("0204", "하루에 최대 예약 가능한 횟수를 초과하였습니다."),
    RESERVATION_FAIL_EXCEED_MAX_PERIOD("0205", "현재 예약 불가능한 날짜입니다."),
    RESERVATION_START_TIME_EMPTY("0206", "예약 시작시간을 입력해주세요."),
    RESERVATION_END_TIME_EMPTY("0207", "예약 종료시간을 입력해주세요."),
    RESERVATION_SEARCH_SUCCESS("0208", "예약 조회 성공");

    private final String code;
    private final String message;

    ResponseConditionCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }
}
