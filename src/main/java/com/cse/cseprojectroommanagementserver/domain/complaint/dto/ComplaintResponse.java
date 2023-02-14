package com.cse.cseprojectroommanagementserver.domain.complaint.dto;

import com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResponse;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;

import static com.cse.cseprojectroommanagementserver.domain.projectroom.dto.ProjectRoomResponse.*;


public class ComplaintResponse {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class AdminComplaintSearchResponse {
        private Long complaintId;
        private String subject;
        private String content;
        private Image image;
        private SimpleProjectRoomResponse projectRoom;
    }
}
