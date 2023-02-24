package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_FORMAT;

public class PenaltyReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ImposePenaltyReq {
        private Long memberId;
        private String description;

        @DateTimeFormat(pattern =LOCAL_DATE_FORMAT)
        private LocalDate startDt;

        @DateTimeFormat(pattern =LOCAL_DATE_FORMAT)
        private LocalDate endDt;

        public Penalty toEntity(Member member) {
            return Penalty.builder()
                    .description(description)
                    .member(member)
                    .startDate(startDt)
                    .endDate(endDt)
                    .build();
        }
    }
}
