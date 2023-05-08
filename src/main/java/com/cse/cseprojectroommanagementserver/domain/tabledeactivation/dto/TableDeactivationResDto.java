package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto;

import lombok.*;

import java.time.LocalDateTime;

public class TableDeactivationResDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableDeactivationSearchRes {
        private String roomName;
        private Long projectTableId;
        private String tableName;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private String reason;
    }
}
