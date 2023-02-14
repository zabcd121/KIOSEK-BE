package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import com.cse.cseprojectroommanagementserver.domain.reservation.dto.ReservationResponseDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResponseDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_FORMAT;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_TIME_FORMAT;

public class PenaltyResponse {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class PenaltyLogResponse {

        private Long penaltyId;
        @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
        private LocalDate startDate;

        @DateTimeFormat(pattern = LOCAL_DATE_FORMAT)
        private LocalDate endDate;

        private String description;

        public PenaltyLogResponse of(Penalty penalty) {
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
    public static class SearchPenaltyByPagingResponse {
        private PenaltyLogResponse penalty;
        private MemberSimpleInfo member;
    }
}
