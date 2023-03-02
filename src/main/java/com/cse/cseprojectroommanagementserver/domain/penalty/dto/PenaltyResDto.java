package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_FORMAT;

public class PenaltyResDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PenaltyLogRes {

        private Long penaltyId;
        @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
        private LocalDate startDt;

        @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
        private LocalDate endDt;

        private String description;

        public PenaltyLogRes of(Penalty penalty) {
            this.penaltyId = penalty.getPenaltyId();
            this.startDt = penalty.getStartDt();
            this.endDt = penalty.getEndDt();
            this.description = penalty.getDescription();

            return this;
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SearchPenaltyByPagingRes {
        private PenaltyLogRes penalty;
        private MemberSimpleInfoRes member;
    }
}
