package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TableDeactivationRequestDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableDeactivationRequest {
        private Long projectRoomId;
        private List<Long> projectTableIdList = new ArrayList<>();
        private TableDeactivationInfoRequest tableDeactivationInfoRequest;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableDeactivationInfoRequest {
        private String reason;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public TableDeactivationInfo toEntity() {
            return TableDeactivationInfo.builder()
                    .reason(reason)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
        }
    }
}
