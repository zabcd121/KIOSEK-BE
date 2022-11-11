package com.cse.cseprojectroommanagementserver.domain.violation.domain.model;

public enum ProcessingStatus {
    /**
     * PENALIZED : 이미 처벌이 진행된 상태
     * NON_PENALIZED : 아직 처벌로 이어지지 않은 상태(횟수가 덜 채워져서)
     * CANCELED : 위반 내역을 무효화 시켜줘서 취소된 상태(현재로서는 관리자가 위반횟수를 조절했을 때 정책에 의해 초기화됨)
     */
    PENALIZED,
    NON_PENALIZED,
    CANCELED
}
