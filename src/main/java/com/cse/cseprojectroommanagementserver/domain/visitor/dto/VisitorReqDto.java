package com.cse.cseprojectroommanagementserver.domain.visitor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class VisitorReqDto {

    @AllArgsConstructor
    @Getter
    public static class VisitorCountDuringPeriodReq {
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
