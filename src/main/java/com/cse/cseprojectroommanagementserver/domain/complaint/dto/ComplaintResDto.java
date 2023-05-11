package com.cse.cseprojectroommanagementserver.domain.complaint.dto;

import com.cse.cseprojectroommanagementserver.global.dto.Image;
import lombok.*;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResDto.*;


public class ComplaintResDto {
    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class AdminComplaintSearchRes {
        private Long complaintId;
        private String subject;
        private String content;
        private Image image;
        private SimpleProjectRoomRes projectRoom;
    }
}
