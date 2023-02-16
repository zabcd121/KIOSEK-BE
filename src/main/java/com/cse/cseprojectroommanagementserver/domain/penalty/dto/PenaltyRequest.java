package com.cse.cseprojectroommanagementserver.domain.penalty.dto;

import com.cse.cseprojectroommanagementserver.domain.member.domain.model.Member;
import com.cse.cseprojectroommanagementserver.domain.penalty.domain.model.Penalty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_FORMAT;

public class PenaltyRequest {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ImposePenaltyRequest {
        private Long memberId;
        private String description;

        @DateTimeFormat(pattern =LOCAL_DATE_FORMAT)
        private LocalDate startDate;

        @DateTimeFormat(pattern =LOCAL_DATE_FORMAT)
        private LocalDate endDate;

        public Penalty toEntity(Member member) {
            return Penalty.builder()
                    .description(description)
                    .member(member)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
        }
    }
}