package com.cse.cseprojectroommanagementserver.domain.complaint.dto;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

public class ComplaintRequest {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ComplainRequest {
        private Long projectRoomId;
        private String subject;
        private String content;
        private MultipartFile image;
    }
}
