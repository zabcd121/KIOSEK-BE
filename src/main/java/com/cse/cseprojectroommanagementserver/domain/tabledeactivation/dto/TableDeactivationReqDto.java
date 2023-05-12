package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.dto;

import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_TIME_FORMAT;

public class TableDeactivationReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class TableDeactivationReq {

        @NotNull
        private Long projectRoomId;

        @NotNull
        @Builder.Default
        private List<Long> projectTableIdList = new ArrayList<>();

        @NotNull
        private TableDeactivationInfoReq tableDeactivationInfoReq;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class TableDeactivationInfoReq {
        @NotNull
        private String reason;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endAt;

        public TableDeactivationInfo toEntity() {
            return TableDeactivationInfo.builder()
                    .reason(this.reason)
                    .startAt(this.startAt)
                    .endAt(this.endAt)
                    .build();
        }
    }
}
