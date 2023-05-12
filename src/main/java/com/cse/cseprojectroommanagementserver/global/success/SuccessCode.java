package com.cse.cseprojectroommanagementserver.global.success;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum SuccessCode {
    /**
     * Signup: 01
     */
    SIGNUP_SUCCESS(CREATED, "0100", "회원가입 성공"),

    LOGIN_ID_DUPLICATION_CHECK_SUCCESS_USABLE(OK, "0103", "사용 가능한 아이디 입니다."),

    EMAIL_DUPLICATION_CHECK_SUCCESS_USABLE(OK, "0105", "사용 가능한 이메일 입니다."),
    AUTH_CODE_SEND_SUCCESS(OK, "0114", "인증 코드 전송이 완료되었습니다."),
    AUTH_CODE_VERIFY_SUCCESS(OK, "0117", "인증코드 검증 완료"),


    /**
     * Auth: 02
     */
    LOGIN_SUCCESS(OK, "0200", "로그인 성공"),
    LOGOUT_SUCCESS(OK, "0205", "로그아웃 성공"),
    TOKEN_REISSUE_SUCCESS(CREATED, "0214", "토큰이 재발급 되었습니다."),
    MEMBER_INFO_REISSUE_SUCCESS(OK, "0215", "회원 정보 재발급"),
    MYPAGE_SEARCH_SUCCESS(OK, "0216", "마이페이지 정보 조회 성공했습니다."),

    /**
     * Reservation: 03
     */
    RESERVE_SUCCESS(CREATED, "0300", "예약 성공"),
    RESERVATION_SEARCH_SUCCESS(OK, "0308", "예약 조회 성공"),
    RESERVATION_CANCEL_SUCCESS(OK, "0310", "예약 취소 성공"),
    CHECKIN_SUCCESS(OK, "0313", "예약 QR 체크인 성공"),

    /**
     * Table Return: 04
     */
    RETURN_SUCCESS(OK, "0400", "반납 성공"),

    /**
     * Project Room: 05
     */
    PROJECT_ROOM_SEARCH_SUCCESS(OK, "0500", "프로젝트실 조회 성공"),

    /**
     * Penalty: 06
     */
    PENALTY_LOGS_SEARCH_SUCCESS(OK, "0600", "제재 내역 조회 성공"),
    PENALTY_IMPOSITION_SUCCESS(CREATED, "0601", "제재 성공"),

    /**
     * Complaint: 07
     */
    COMPLAIN_SUCCESS(CREATED, "0700", "민원 접수 성공"),
    COMPLAINT_SEARCH_SUCCESS(OK, "0701", "민원 조회 성공"),

    /**
     * Reservation Policy: 08
     */
    RESERVATION_POLICY_CHANGE_SUCCESS(CREATED, "0800", "예약 정책 수정 성공"),
    RESERVATION_POLICY_SEARCH_SUCCESS(OK, "0802", "예약 정책 조회 성공"),

    /**
     * Penalty Policy: 09
     */
    PENALTY_POLICY_CHANGE_SUCCESS(CREATED, "0900", "제재 정책 수정 성공"),
    PENALTY_POLICY_SEARCH_SUCCESS(OK, "0902", "제재 정책 조회 성공"),

    /**
     * Table Deactivation: 10
     */
    TABLE_DEACTIVATE_SUCCESS(CREATED, "1000", "테이블 비활성화 성공"),
    TABLE_DEACTIVATION_SEARCH_SUCCESS(OK, "1003", "테이블 비활성화 내역 조회 성공");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
