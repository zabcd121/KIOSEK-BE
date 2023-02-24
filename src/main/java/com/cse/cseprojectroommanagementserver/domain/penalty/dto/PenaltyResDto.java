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
        private LocalDate startDate;

        @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
        private LocalDate endDate;

        private String description;

        public PenaltyLogRes of(Penalty penalty) {
            this.penaltyId = penalty.getPenaltyId();
            this.startDate = penalty.getStartDate();
            this.endDate = penalty.getEndDate();
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
