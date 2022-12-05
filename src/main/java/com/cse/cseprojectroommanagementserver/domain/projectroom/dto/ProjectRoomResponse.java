package com.cse.cseprojectroommanagementserver.domain.projectroom.dto;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.projecttable.dto.ProjectTableResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cseprojectroommanagementserver.domain.projecttable.dto.ProjectTableResponse.*;

public class ProjectRoomResponse {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter @Setter
    public static class ProjectRoomAndTableSearchResponse {
        private Integer priority;
        private Long projectRoomId;
        private String roomName;
        private List<ProjectTableSearchResponse> projectTableList = new ArrayList<>();

        public ProjectRoomAndTableSearchResponse of(ProjectRoom projectRoom, List<ProjectTable> projectTableList) {
            this.priority = projectRoom.getPriority();
            this.projectRoomId = projectRoom.getProjectRoomId();
            this.roomName = projectRoom.getRoomName();

            for (ProjectTable projectTable : projectTableList) {
                this.projectTableList.add(new ProjectTableSearchResponse().of(projectTable));
            }

            return this;
        }
    }
}
