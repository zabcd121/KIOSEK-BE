package com.cse.cseprojectroommanagementserver.domain.projectroom.dto;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projecttable.dto.ProjectTableResDto.*;

public class ProjectRoomResDto {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter @Setter
    public static class ProjectRoomAndTableSearchRes {
        private Integer priority;
        private Long projectRoomId;
        private String roomName;
        private List<ProjectTableSearchRes> projectTableList = new ArrayList<>();

        public ProjectRoomAndTableSearchRes of(ProjectRoom projectRoom, List<ProjectTable> projectTableList) {
            this.priority = projectRoom.getPriority();
            this.projectRoomId = projectRoom.getProjectRoomId();
            this.roomName = projectRoom.getRoomName();

            for (ProjectTable projectTable : projectTableList) {
                this.projectTableList.add(new ProjectTableSearchRes().of(projectTable));
            }

            return this;
        }
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class SimpleProjectRoomRes {
        private Long projectRoomId;
        private String buildingName;
        private String roomName;
    }
}
