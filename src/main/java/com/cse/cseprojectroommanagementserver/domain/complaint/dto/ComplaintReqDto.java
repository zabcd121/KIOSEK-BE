package com.cse.cseprojectroommanagementserver.domain.complaint.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ComplaintReqDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ComplainReq {
        @NotEmpty
        private Long projectRoomId;

        @NotEmpty
        private String subject;

        @NotEmpty
        private String content;

        @NotEmpty
        private MultipartFile photo;
    }
}
