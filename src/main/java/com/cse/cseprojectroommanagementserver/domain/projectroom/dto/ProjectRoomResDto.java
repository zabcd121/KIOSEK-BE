package com.cse.cseprojectroommanagementserver.domain.projectroom.dto;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cse.cseprojectroommanagementserver.domain.projecttable.dto.ProjectTableResDto.*;

public class ProjectRoomResDto {

    @Builder(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProjectRoomAndTableSearchRes {
        private Integer priority;
        private Long projectRoomId;
        private String roomName;
        @Builder.Default
        private List<ProjectTableSearchRes> projectTableList = new ArrayList<>();

        public static ProjectRoomAndTableSearchRes of(ProjectRoom projectRoom, List<ProjectTable> projectTableList) {
            return ProjectRoomAndTableSearchRes.builder()
                    .priority(projectRoom.getPriority())
                    .projectRoomId(projectRoom.getProjectRoomId())
                    .roomName(projectRoom.getRoomName())
                    .projectTableList(projectTableList.stream().map(ProjectTableSearchRes::of).collect(Collectors.toList()))
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class SimpleProjectRoomRes {
        private Long projectRoomId;
        private String buildingName;
        private String roomName;
    }
}
