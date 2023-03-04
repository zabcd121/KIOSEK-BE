package com.cse.cseprojectroommanagementserver.domain.complaint.dto;

import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResDto.*;


public class ComplaintResDto {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    public static class AdminComplaintSearchRes {
        private Long complaintId;
        private String subject;
        private String content;
        private Image image;
        private SimpleProjectRoomRes projectRoom;
    }
}
