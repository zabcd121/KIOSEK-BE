package com.cse.cseprojectroommanagementserver.domain.complaint.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class ComplaintReqDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ComplainReq {
        private Long projectRoomId;
        private String subject;
        private String content;
        private MultipartFile image;
    }
}
