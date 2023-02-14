package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TableDeactivationResponseDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class AdminTableDeactivationSearchResponse {
        private String roomName;
        private String tableName;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private String reason;
    }
}
