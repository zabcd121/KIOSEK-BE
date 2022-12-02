package com.cse.cseprojectroommanagementserver.domain.tablereturn.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class TableReturnRequestDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class TableReturnRequest {
        private Long reservationId;
        private MultipartFile cleanupPhoto;
    }
}
