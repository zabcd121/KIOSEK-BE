package com.cse.cseprojectroommanagementserver.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    /**
     * Signup: 01
     */

    FILE_SYSTEM_ERR_ACCOUNT_QR(INTERNAL_SERVER_ERROR, "0113", "QR 코드 생성에 실패했습니다."),
    DUPLICATED_LOGINID(CONFLICT, "0102", "이미 사용중인 아이디(학번) 입니다.)"),
    DUPLICATED_EMAIL(CONFLICT, "0104", "이미 사용중인 이메일 입니다."),
    INVALID_VALUE_LOGIN_ID(BAD_REQUEST, "0106", "잘못된 형식의 아이디(학번)이 입력되었습니다."),
    INVALID_VALUE_PASSWORD(BAD_REQUEST, "0107", "잘못된 형식의 비밃번호가 입력되었습니다. 최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다."),
    INVALID_VALUE_EMAIL(BAD_REQUEST, "0108", "잘못된 형식의 이메일이 입력되었습니다."),
    INVALID_VALUE_NAME(BAD_REQUEST, "0111", "이름 형식이 잘못 입력되었습니다."),
    INPUT_NULL_EMAIL(BAD_REQUEST, "0110", "이메일이 입력되지 않았습니다."),
    INPUT_NULL_NAME(BAD_REQUEST, "0112", "이름이 입력되지 않았습니다."),
    EXPIRED_AUTH_CODE(NOT_FOUND, "0115", "만료된 인증코드입니다."),
    INCORRECT_AUTH_CODE(CONFLICT, "0116", "잘못된 인증코드입니다."),
    UNAUTHENTICATED_AUTH_CODE(CONFLICT, "0118", "인증코드 검증 먼저 해주세요."),



    /**
     * Auth: 02
     */
    INPUT_NULL_LOGIN_ID(BAD_REQUEST, "0201", "아이디가 입력되지 않았습니다."),
    INPUT_NULL_PASSWORD(BAD_REQUEST, "0202", "패스워드가 입력되지 않았습니다."),
    ID_NOT_FOUND(UNAUTHORIZED, "0203", "존재하지 않는 아이디입니다."),
    INCORRECT_PASSWORD(UNAUTHORIZED, "0204", "잘못된 비밀번호를 입력하였습니다."),

    HEADER_NULL_TOKEN(BAD_REQUEST, "0206", "인증 토큰이 전달되지 않았습니다."),
    INVALID_TYPE_TOKEN(BAD_REQUEST, "0207", "Bearer 토큰이 전달되지 않았습니다."),
    UNKNOWN_PROBLEM_TOKEN(BAD_REQUEST, "0208", "알 수 없는 토큰 에러입니다."),
    INCORRECT_SIGNATURE(BAD_REQUEST, "0209", "잘못된 서명이 전달되었습니다."),
    EXPIRED_TOKEN(BAD_REQUEST, "0210", "만료된 토큰이 전달되었습니다."),
    UNSUPPORTED_TOKEN(BAD_REQUEST, "0211", "지원되지 않는 토큰이 전달되었습니다."),
    UNAUTHORIZED_MEMBER(FORBIDDEN, "0212", "권한 부족 접근 거부"),
    NOT_FOUND_REFRESH_TOKEN_IN_STORE(NOT_FOUND, "0213", "존재하지 않는 refresh token 입니다."),
    EXPIRED_REFRESH_TOKEN(UNAUTHORIZED, "0217", "refresh token이 만료되었습니다."),
    NOT_FOUND_ACCOUNT_QR(UNAUTHORIZED, "0218", "유효하지 않은 QR 코드입니다."),
    NOT_FOUND_MEMBER(NOT_FOUND, "0219", "존재하지 않는 회원입니다."),

    /**
     * Reservation: 03
     */
    UNAUTHORIZED_PENALTY_MEMBER(FORBIDDEN, "0301", "계정 정지 상태이므로 예약이 불가능합니다."),
    DUPLICATED_RESERVATION(CONFLICT, "0302", "중복된 예약이 존재합니다."),
    MAX_TIME_LIMIT_POLICY_INFRACTION(CONFLICT, "0303", "예약가능 최대시간을 초과하였습니다."),
    MAX_COUNT_LIMIT_POLICY_INFRACTION(CONFLICT, "0304", "하루에 최대 예약 가능한 횟수를 초과하였습니다."),
    MAX_PERIOD_LIMIT_POLICY_INFRACTION(CONFLICT, "0305", "현재 예약 불가능한 날짜입니다."),
    INVALID_VALUE_ENDAT(BAD_REQUEST, "0319", "종료시간이 시작시간보다 빠릅니다."),
    INPUT_NULL_STARTAT(BAD_REQUEST, "0306", "예약 시작시간을 입력해주세요."),
    INPUT_NULL_ENDAT(BAD_REQUEST, "0307", "예약 종료시간을 입력해주세요."),
    FILE_SYSTEM_ERR_RESERVATION_QR(INTERNAL_SERVER_ERROR, "0312", "예약 QR 생성 실패"),
    NOT_FOUND_RESERVATION(NOT_FOUND, "0309", "예약 조회 실패"),
    UNAUTHORIZED_RESERVATION(CONFLICT, "0311", "예약에 대한 권한이 없습니다."),
    IRREVOCABLE_RESERVATION_STATUS(CONFLICT, "0321", "예약 취소가 불가능한 상태입니다."),
    INCORRECT_RESERVATION_QR(UNAUTHORIZED, "0316", "예약 QR 체크인 실패"),
    CHECKIN_TIME_POLICY_INFRACTION(CONFLICT, "0317", "체크인 불가능한 시간입니다."),
    CHECKIN_IMPOSSIBLE_STATUS(CONFLICT, "0318", "이전 예약 사용중에는 체크인이 불가능합니다."),
    DISABLED_TABLE(CONFLICT, "0316", "현재 사용 불가능한 테이블입니다."),
    INVALID_VALUE_RESERVATION_STATUS(BAD_REQUEST, "0320", "잘못된 예약 상태나 코드를 입력받아 변환에 실패했습니다."),

    /**
     * Table Return: 04
     */
    UNAUTHORIZED_RETURN(FORBIDDEN, "0401", "반납 실패. 권한 부족"),
    FILE_SYSTEM_ERR_RETURN_IMAGE(INTERNAL_SERVER_ERROR, "0402", "반납 실패. 이미지 업로드 실패"),
    RETURN_IMPOSSIBLE_STATUS(CONFLICT, "0403", "반납 실패. 반납 불가능한 상태입니다."),

    /**
     * Project Room: 05
     */
    NOT_FOUND_PROJECT_ROOM(CONFLICT, "0501", "프로젝트실 조회 실패"),

    /**
     * Penalty: 06
     */
    ALREADY_SUSPENDED(CONFLICT, "0602", "제재 실패 이미 해당일까지 제재 진행중인 회원입니다."),

    /**
     * Complaint: 07
     */
    //COMPLAIN_FAIL(CONFLICT, "0702", "민원 접수 실패"),
    FILE_SYSTEM_ERR_COMPLAIN_IMAGE(INTERNAL_SERVER_ERROR, "0703", "민원 접수 실패, 이미지 업로드 실패"),

    /**
     * Reservation Policy: 08
     */
    NOT_FOUND_RESERVATION_POLICY(NOT_FOUND, "0801", "예약 정책 조회 실패"),

    /**
     * Penalty Policy: 09
     */
    NOT_FOUND_PENALTY_POLICY(NOT_FOUND, "0901", "제재 정책 조회 실패"),

    /**
     * Table Deactivation: 10
     */
    DUPLICATED_TABLE_DEACTIVATION(CONFLICT, "1002", "중복된 비활성화 내역으로 인한 실패"),

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
