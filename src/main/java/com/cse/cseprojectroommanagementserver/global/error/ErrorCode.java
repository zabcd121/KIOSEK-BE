package com.cse.cseprojectroommanagementserver.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /**
     * Signup: 01
     */
    SIGNUP_FAIL(CONFLICT, "0101", "회원가입 실패"),

    SIGNUP_FAIL_ACCOUNT_QR_CREATE_FAIL(INTERNAL_SERVER_ERROR, "0113", "QR 코드 생성에 실패했습니다."),
    LOGIN_ID_DUPLICATION_CHECK_FAIL_UNUSABLE(CONFLICT, "0102", "이미 사용중인 아이디(학번) 입니다.)"),
    EMAIL_DUPLICATION_CHECK_FAIL_UNUSABLE(CONFLICT, "0104", "이미 사용중인 이메일 입니다."),
    BAD_REQUEST_LOGIN_ID_INVALID_TYPE(BAD_REQUEST, "0106", "잘못된 형식의 아이디(학번)이 입력되었습니다."),
    BAD_REQUEST_PASSWORD_INVALID_TYPE(BAD_REQUEST, "0107", "잘못된 형식의 비밃번호가 입력되었습니다. 최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다."),
    BAD_REQUEST_EMAIL_INVALID_TYPE(BAD_REQUEST, "0108", "잘못된 형식의 이메일이 입력되었습니다."),

    @Deprecated
    EMAIL_AUTH_FAIL(CONFLICT, "0109", "이메일 인증 실패"),

    BAD_REQUEST_EMAIL_NULL(BAD_REQUEST, "0110", "이메일이 입력되지 않았습니다."),
    BAD_REQUEST_NAME_INVALID_TYPE(BAD_REQUEST, "0111", "이름 형식이 잘못 입력되었습니다."),
    BAD_REQUEST_NAME_NULL(BAD_REQUEST, "0112", "이름이 입력되지 않았습니다."),
    AUTH_CODE_VERIFY_FAIL_EXPIRED(NOT_FOUND, "0115", "이 메일로 인증 코드를 전송한 내역이 없습니다."),
    AUTH_CODE_VERIFY_FAIL_WRONG(CONFLICT, "0116", "잘못된 인증코드입니다."),
    AUTH_CODE_NOT_YET_VERIFIED(CONFLICT, "0118", "인증코드 검증 먼저 해주세요."),



    /**
     * Auth: 02
     */
    BAD_REQUEST_LOGIN_ID_NULL(BAD_REQUEST, "0201", "아이디가 입력되지 않았습니다."),
    BAD_REQUEST_PASSWORD_NULL(BAD_REQUEST, "0202", "패스워드가 입력되지 않았습니다."),
    LOGIN_FAIL_ID_NOT_EXIST(UNAUTHORIZED, "0203", "존재하지 않는 아이디입니다."),
    LOGIN_FAIL_PASSWORD_WRONG(UNAUTHORIZED, "0204", "잘못된 비밀번호를 입력하였습니다."),

    BAD_REQUEST_TOKEN_NOT_PASSED(BAD_REQUEST, "0206", "인증 토큰이 전달되지 않았습니다."),
    BAD_REQUEST_TOKEN_NOT_BEARER(BAD_REQUEST, "0207", "Bearer 토큰이 전달되지 않았습니다."),
    BAD_REQUEST_TOKEN_WRONG_TYPE(BAD_REQUEST, "0208", "잘못된 타입의 인증 토큰이 전달되었습니다."),
    BAD_REQUEST_TOKEN_WRONG_SIGNATURE(BAD_REQUEST, "0209", "잘못된 서명이 전달되었습니다."),
    BAD_REQUEST_TOKEN_EXPIRED(BAD_REQUEST, "0210", "만료된 토큰이 전달되었습니다."),
    BAD_REQUEST_TOKEN_UNSUPPORTED(BAD_REQUEST, "0211", "지원되지 않는 토큰이 전달되었습니다."),
    ACCESS_FAIL_NO_AUTHORITY(FORBIDDEN, "0212", "권한 부족 접근 거부"),
    TOKEN_REISSUE_FAIL_REFRESH_TOKEN_NOT_FOUND_IN_STORE(NOT_FOUND, "0213", "존재하지 않는 refresh token 입니다."),
    TOKEN_REISSUE_FAIL_REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "0217", "refresh token이 만료되었습니다."),
    ONSITE_RESERVE_FAIL_ACCOUNT_QR_WRONG(UNAUTHORIZED, "0218", "유효하지 않은 QR 코드입니다."),
    SEARCH_MEMBER_FAIL_NOT_FOUND(NOT_FOUND, "0219", "존재하지 않는 회원입니다."),

    /**
     * Reservation: 03
     */
    RESERVE_FAIL_PENALTY_USER(FORBIDDEN, "0301", "계정 정지 상태이므로 예약이 불가능합니다."),
    RESERVE_FAIL_DUPLICATED(CONFLICT, "0302", "중복된 예약이 존재합니다."),
    RESERVE_FAIL_EXCEED_MAX_TIME(CONFLICT, "0303", "예약가능 최대시간을 초과하였습니다."),
    RESERVE_FAIL_EXCEED_MAX_COUNT(CONFLICT, "0304", "하루에 최대 예약 가능한 횟수를 초과하였습니다."),
    RESERVE_FAIL_EXCEED_MAX_PERIOD(CONFLICT, "0305", "현재 예약 불가능한 날짜입니다."),
    RESERVE_FAIL_ENDAT_BEFORE_STARTAT(BAD_REQUEST, "0319", "종료시간이 시작시간보다 빠릅니다."),
    RESERVE_FAIL_START_TIME_EMPTY(BAD_REQUEST, "0306", "예약 시작시간을 입력해주세요."),
    RESERVE_FAIL_END_TIME_EMPTY(BAD_REQUEST, "0307", "예약 종료시간을 입력해주세요."),
    RESERVE_FAIL_RESERVATION_QR_CREATE_FAIL(INTERNAL_SERVER_ERROR, "0312", "예약 QR 생성 실패"),
    RESERVATION_SEARCH_FAIL_NOT_FOUND(NOT_FOUND, "0309", "예약 조회 실패"),
    RESERVATION_CANCEL_FAIL_NO_AUTHORITY(CONFLICT, "0311", "예약에 대한 권한이 없습니다."),
    RESERVATION_CANCEL_FAIL_UNABLE_STATUS(CONFLICT, "0321", "예약 취소가 불가능한 상태입니다."),
    CHECKIN_FAIL_WRONG_RESERVATION_QR(UNAUTHORIZED, "0316", "예약 QR 체크인 실패"),
    CHECKIN_FAIL_UNABLE_TIME_TO_CHECKIN(CONFLICT, "0317", "체크인 불가능한 시간입니다."),
    CHECKIN_FAIL_UNABLE_STATUS(CONFLICT, "0318", "이전 예약 사용중에는 체크인이 불가능합니다."),
    RESERVATION_FAIL_DISABLED_TABLE(CONFLICT, "0316", "현재 사용 불가능한 테이블입니다."),
    RESERVATION_STATUS_CONVERT_FAIL(BAD_REQUEST, "0320", "잘못된 예약 상태나 코드를 입력받아 변환에 실패했습니다."),

    /**
     * Table Return: 04
     */
    RETURN_FAIL_NO_AUTHORITY(FORBIDDEN, "0401", "반납 실패. 권한 부족"),
    RETURN_FAIL_IMAGE_UPLOAD_FAIL(INTERNAL_SERVER_ERROR, "0402", "반납 실패. 이미지 업로드 실패"),
    RETURN_FAIL_UNABLE_STATUS(CONFLICT, "0403", "반납 실패. 반납 불가능한 상태입니다."),

    /**
     * Project Room: 05
     */
    PROJECT_ROOM_SEARCH_FAIL(CONFLICT, "0501", "프로젝트실 조회 실패"),

    /**
     * Penalty: 06
     */
    PENALTY_IMPOSE_FAIL_ALREADY_SUSPENDED_UNTIL_REQUEST_DATETIME(CONFLICT, "0602", "제재 실패 이미 해당일까지 제재 진행중인 회원입니다."),

    /**
     * Complaint: 07
     */
    COMPLAIN_FAIL(CONFLICT, "0702", "민원 접수 실패"),
    COMPLAIN_FAIL_IMAGE_UPLOAD_FAIL(INTERNAL_SERVER_ERROR, "0703", "민원 접수 실패, 이미지 업로드 실패"),

    /**
     * Reservation Policy: 08
     */
    RESERVATION_POLICY_SEARCH_FAIL(NOT_FOUND, "0801", "예약 정책 조회 실패"),

    /**
     * Penalty Policy: 09
     */
    PENALTY_POLICY_SEARCH_FAIL_NOT_FOUND(NOT_FOUND, "0901", "제재 정책 조회 실패"),

    /**
     * Table Deactivation: 10
     */
    TABLE_DEACTIVATE_FAIL_DUPLICATED(CONFLICT, "1002", "중복된 비활성화 내역으로 인한 실패"),

    /**
     * Common: 11
     */
    INVALID_INPUT_VALUE(BAD_REQUEST, "1100", "입력값이 잘못되었습니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "1101", "서버 내부 에러");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
