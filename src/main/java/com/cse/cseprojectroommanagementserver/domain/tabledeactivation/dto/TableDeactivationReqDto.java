package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TableDeactivationReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableDeactivationReq {
        private Long projectRoomId;
        private List<Long> projectTableIdList = new ArrayList<>();
        private TableDeactivationInfoReq tableDeactivationInfoReq;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableDeactivationInfoReq {
        private String reason;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public TableDeactivationInfo toEntity() {
            return TableDeactivationInfo.builder()
                    .reason(reason)
                    .startAt(startTime)
                    .endAt(endTime)
                    .build();
        }
    }
}
