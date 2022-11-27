package com.cse.cseprojectroommanagementserver.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.*;

public class ReservationRequestDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ReserveRequest {
        private Long projectTableId;
        private Long memberId;

        @NotBlank
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_FORMAT)
        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime startDateTime;

        @NotBlank
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = LOCAL_DATE_TIME_FORMAT)
        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime endDateTime;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter @Setter
    public static class FirstAndLastDateTimeRequest {
        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime firstDateTime;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime lastDateTime;
    }
}
