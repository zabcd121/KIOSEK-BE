package com.cse.cseprojectroommanagementserver.domain.tablereturn.dto;

import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.cse.cseprojectroommanagementserver.global.util.DateFormatProvider.LOCAL_DATE_TIME_FORMAT;

public class TableReturnResDto {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableReturnSimpleInfoRes {
        private Long tableReturnId;

        @DateTimeFormat(pattern = LOCAL_DATE_TIME_FORMAT)
        private LocalDateTime returnedAt;

        private Image cleanUpPhoto;
    }
}
