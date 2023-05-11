package com.cse.cseprojectroommanagementserver.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ResConditionCode {
    /**
     * Signup: 01
     */
    SIGNUP_SUCCESS(CREATED, "0100", "회원가입 성공"),
    SIGNUP_FAIL(CONFLICT, "0101", "회원가입 실패"),
    LOGIN_ID_DUPLICATION_CHECK_FAIL_UNUSABLE(CONFLICT, "0102", "이미 사용중인 아이디(학번) 입니다.)"),
    LOGIN_ID_DUPLICATION_CHECK_SUCCESS_USABLE(OK, "0103", "사용 가능한 아이디 입니다."),
    EMAIL_DUPLICATION_CHECK_FAIL_UNUSABLE(CONFLICT, "0104", "이미 사용중인 이메일 입니다."),
    EMAIL_DUPLICATION_CHECK_SUCCESS_USABLE(OK, "0105", "사용 가능한 이메일 입니다."),
    BAD_REQUEST_LOGIN_ID_INVALID_TYPE(BAD_REQUEST, "0106", "잘못된 형식의 아이디(학번)이 입력되었습니다."),
    BAD_REQUEST_PASSWORD_INVALID_TYPE(BAD_REQUEST, "0107", "잘못된 형식의 비밃번호가 입력되었습니다. 최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다."),
    BAD_REQUEST_EMAIL_INVALID_TYPE(BAD_REQUEST, "0108", "잘못된 형식의 이메일이 입력되었습니다."),

    @Deprecated
    EMAIL_AUTH_FAIL(CONFLICT, "0109", "이메일 인증 실패"),

    BAD_REQUEST_EMAIL_NULL(BAD_REQUEST, "0110", "이메일이 입력되지 않았습니다."),
    BAD_REQUEST_NAME_INVALID_TYPE(BAD_REQUEST, "0111", "이름 형식이 잘못 입력되었습니다."),
    BAD_REQUEST_NAME_NULL(BAD_REQUEST, "0112", "이름이 입력되지 않았습니다."),
    ACCOUNT_QR_CREATE_FAIL(INTERNAL_SERVER_ERROR, "0113", "QR 코드 생성에 실패했습니다."),
    AUTH_CODE_SEND_SUCCESS(OK, "0114", "인증 코드 전송이 완료되었습니다."),
    AUTH_CODE_VERIFY_FAIL_NOT_FOUND(NOT_FOUND, "0115", "이 메일로 인증 코드를 전송한 내역이 없습니다."),
    AUTH_CODE_VERIFY_FAIL(CONFLICT, "0116", "잘못된 인증코드입니다."),
    AUTH_CODE_VERIFY_SUCCESS(OK, "0117", "인증코드 검증 완료"),
    AUTH_CODE_NOT_VERIFIED(CONFLICT, "0118", "인증코드 검증 먼저 해주세요."),



    /**
     * Auth: 02
     */
    LOGIN_SUCCESS(OK, "0200", "로그인 성공"),
    BAD_REQUEST_LOGIN_ID_NULL(BAD_REQUEST, "0201", "아이디가 입력되지 않았습니다."),
    BAD_REQUEST_PASSWORD_NULL(BAD_REQUEST, "0202", "패스워드가 입력되지 않았습니다."),
    LOGIN_FAIL_ID_NOT_EXIST(UNAUTHORIZED, "0203", "존재하지 않는 아이디입니다."),
    LOGIN_FAIL_PASSWORD_INVALID(UNAUTHORIZED, "0204", "잘못된 비밀번호를 입력하였습니다."),
    LOGOUT_SUCCESS(OK, "0205", "로그아웃 성공"),

    BAD_REQUEST_TOKEN_NOT_PASSED(BAD_REQUEST, "0206", "인증 토큰이 전달되지 않았습니다."),
    BAD_REQUEST_TOKEN_NOT_BEARER(BAD_REQUEST, "0207", "Bearer 토큰이 전달되지 않았습니다."),
    BAD_REQUEST_TOKEN_WRONG_TYPE(BAD_REQUEST, "0208", "잘못된 타입의 인증 토큰이 전달되었습니다."),
    BAD_REQUEST_TOKEN_WRONG_SIGNATURE(BAD_REQUEST, "0209", "잘못된 서명이 전달되었습니다."),
    BAD_REQUEST_TOKEN_EXPIRED(BAD_REQUEST, "0210", "만료된 토큰이 전달되었습니다."),
    BAD_REQUEST_TOKEN_UNSUPPORTED(BAD_REQUEST, "0211", "지원되지 않는 토큰이 전달되었습니다."),
    ACCESS_FAIL_NO_AUTHORITY(FORBIDDEN, "0212", "권한 부족 접근 거부"),
    TOKEN_REISSUE_FAIL_REFRESH_TOKEN_NOT_EXIST_IN_STORE(NOT_FOUND, "0213", "존재하지 않는 refresh token 입니다."),
    TOKEN_REISSUE_SUCCESS(CREATED, "0214", "토큰이 재발급 되었습니다."),
    MEMBER_INFO_REISSUE_SUCCESS(OK, "0215", "회원 정보 재발급"),
    MYPAGE_SEARCH_SUCCESS(OK, "0216", "마이페이지 정보 조회 성공했습니다."),
    TOKEN_REISSUE_FAIL_REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "0217", "refresh token이 만료되었습니다."),

    /**
     * Reservation: 03
     */
    ONSITE_RESERVE_FAIL_ACCOUNT_QR_INVALID(UNAUTHORIZED, "0218", "유효하지 않은 QR 코드입니다."),
    RESERVE_SUCCESS(CREATED, "0300", "예약 성공"),
    RESERVE_FAIL_PENALTY_USER(FORBIDDEN, "0301", "계정 정지 상태이므로 예약이 불가능합니다."),
    RESERVE_FAIL_DUPLICATED(CONFLICT, "0302", "중복된 예약이 존재합니다."),
    RESERVE_FAIL_EXCEED_MAX_TIME(CONFLICT, "0303", "예약가능 최대시간을 초과하였습니다."),
    RESERVE_FAIL_EXCEED_MAX_COUNT(CONFLICT, "0304", "하루에 최대 예약 가능한 횟수를 초과하였습니다."),
    RESERVE_FAIL_EXCEED_MAX_PERIOD(CONFLICT, "0305", "현재 예약 불가능한 날짜입니다."),
    RESERVE_FAIL_ENDAT_BEFORE_STARTAT(BAD_REQUEST, "0319", "종료시간이 시작시간보다 빠릅니다."),
    RESERVE_FAIL_START_TIME_EMPTY(BAD_REQUEST, "0306", "예약 시작시간을 입력해주세요."),
    RESERVE_FAIL_END_TIME_EMPTY(BAD_REQUEST, "0307", "예약 종료시간을 입력해주세요."),
    RESERVATION_SEARCH_SUCCESS(OK, "0308", "예약 조회 성공"),
    RESERVATION_SEARCH_FAIL(NOT_FOUND, "0309", "예약 조회 실패"),
    RESERVATION_CANCEL_SUCCESS(OK, "0310", "예약 취소 성공"),
    RESERVATION_CANCEL_FAIL(CONFLICT, "0311", "예약 취소 실패"),
    RESERVATION_QR_CREATE_FAIL(INTERNAL_SERVER_ERROR, "0312", "예약 QR 생성 실패"),
    CHECKIN_SUCCESS(OK, "0313", "예약 QR 체크인 성공"),
    CHECKIN_FAIL(UNAUTHORIZED, "0316", "예약 QR 체크인 실패"),
    CHECKIN_FAIL_UNABLE_TIME_TO_CHECKIN(CONFLICT, "0317", "체크인 불가능한 시간입니다."),
    CHECKIN_FAIL_UNABLE_STATUS_TO_CHECKIN(CONFLICT, "0318", "이전 예약 사용중에는 체크인이 불가능합니다."),
    RESERVATION_FAIL_DISABLED_TABLE(CONFLICT, "0316", "현재 사용 불가능한 테이블입니다."),

    /**
     * Table Return: 04
     */
    RETURN_SUCCESS(OK, "0400", "반납 성공"),
    RETURN_FAIL_NO_AUTHORITY(FORBIDDEN, "0401", "반납 실패. 권한 부족"),

    /**
     * Project Room: 05
     */
    PROJECT_ROOM_SEARCH_SUCCESS(OK, "0500", "프로젝트실 조회 성공"),

    /**
     * Penalty: 06
     */
    PENALTY_LOGS_SEARCH_SUCCESS(OK, "0600", "제재 내역 조회 성공"),
    PENALTY_IMPOSITION_SUCCESS(CREATED, "0601", "제재 성공"),
    PENALTY_IMPOSITION_FAIL(BAD_REQUEST, "0602", "제재 실패 이미 해당일까지 제재 진행중인 회원입니다."),

    /**
     * Complaint: 07
     */
    COMPLAIN_SUCCESS(CREATED, "0700", "민원 접수 성공"),
    COMPLAINT_SEARCH_SUCCESS(OK, "0701", "민원 조회 성공"),

    /**
     * Reservation Policy: 08
     */
    RESERVATION_POLICY_CHANGE_SUCCESS(CREATED, "0800", "예약 정책 수정 성공"),
    RESERVATION_POLICY_SEARCH_FAIL(NOT_FOUND, "0801", "예약 정책 조회 실패"),
    RESERVATION_POLICY_SEARCH_SUCCESS(OK, "0802", "예약 정책 조회 성공"),

    /**
     * Penalty Policy: 09
     */
    PENALTY_POLICY_CHANGE_SUCCESS(CREATED, "0900", "제재 정책 수정 성공"),
    PENALTY_POLICY_SEARCH_FAIL(NOT_FOUND, "0901", "제재 정책 조회 실패"),
    PENALTY_POLICY_SEARCH_SUCCESS(OK, "0902", "제재 정책 조회 성공"),

    /**
     * Table Deactivation: 10
     */
    TABLE_DEACTIVATE_SUCCESS(CREATED, "1000", "테이블 비활성화 성공"),
    //TABLE_DEACTIVATE_FAIL("1001", "테이블 비활성화 실패"),
    TABLE_DEACTIVATE_DUPLICATED(CONFLICT, "1002", "중복된 비활성화 내역으로 인한 실패"),
    TABLE_DEACTIVATION_SEARCH_SUCCESS(OK, "1003", "테이블 비활성화 내역 조회 성공");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ResConditionCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
