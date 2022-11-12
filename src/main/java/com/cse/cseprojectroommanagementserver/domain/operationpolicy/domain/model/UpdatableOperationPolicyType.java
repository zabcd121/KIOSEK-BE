package com.cse.cseprojectroommanagementserver.domain.operationpolicy.domain.model;

public enum UpdatableOperationPolicyType {
    /**
     * 현재 관리자 페이지에서 수정 가능한 정책들
     * 1. 한번에 예약 가능한 시간
     * 2. 몇번 위반해야 정지 당하는지
     * 3. 정지 당하면 며칠 당하는지
     */
    MAX_TIME_AT_A_TIME,
    VIOLATION_COUNT_RESULT_IN_SUSPENSION,
    SUSPENSION_DAY_COUNT,




}
