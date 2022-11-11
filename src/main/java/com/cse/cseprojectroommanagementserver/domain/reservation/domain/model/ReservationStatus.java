package com.cse.cseprojectroommanagementserver.domain.reservation.domain.model;

public enum ReservationStatus {
    RESERVATION_COMPLETED("예약완료"),
    CANCELED("취소됨"),
    IN_USE("사용중"),
    UNUSED("미사용"),
    RETURN_WAITING("반납 대기중"),
    NOT_RETURNED("미반납"),
    RETURNED("반납완료");

    private final String content;

    private ReservationStatus(String content) {
        this.content = content;
    }

    public String getContent() { return content; }
}
