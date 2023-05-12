package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.domain.member.dto.MemberResDto.*;
import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_FORMAT;

public class PenaltyResDto {

    @NoArgsConstructor
    @Getter
    public static class SearchPenaltyByPagingRes {
        private PenaltyLogRes penalty;
        private MemberSimpleInfoRes member;
    }

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

        public static PenaltyLogRes of(Penalty penalty) {
            return new PenaltyLogRes(penalty.getPenaltyId(), penalty.getStartDt(), penalty.getEndDt(), penalty.getDescription());

        }
    }
}
