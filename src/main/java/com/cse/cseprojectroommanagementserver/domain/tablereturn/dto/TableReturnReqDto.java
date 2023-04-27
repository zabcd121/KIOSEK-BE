package com.cse.cseprojectroommanagementserver.domain.tablereturn.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class TableReturnReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TableReturnReq {
        @NotNull
        private Long reservationId;

        @NotNull
        private MultipartFile cleanupPhoto;
    }
}
