package com.cse.cseprojectroommanagementserver.domain.operationpolicy.domain.model;

public enum FixedOperationPolicyType {
    /**
     * 현재 관리자 페이지에서 수정 불가한 정책들(고정)
     * 1. 하루에 당일+미래에 대한 예약을 통틀어 예약할 수 있는 횟수
     * 2. 오늘부터 최대 몇 주 뒤까지 예약 가능한지
     */
    MAX_COUNT_PER_DAY(1),
    MAX_PERIOD_ENABLE_RESERVATION(1);

    private final Integer setValue;

    private FixedOperationPolicyType(Integer setValue) {
        this.setValue = setValue;
    }

    public Integer getSetValue() { return setValue; }
}
