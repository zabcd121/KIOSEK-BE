package com.cse.cseprojectroommanagementserver.domain.projecttable.dto;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.*;

public class ProjectTableResDto {


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProjectTableSearchRes {
        private Long tableId;
        private String tableName;

        public static ProjectTableSearchRes of(ProjectTable projectTable) {
            return new ProjectTableSearchRes(projectTable.getTableId(), projectTable.getTableName());
        }
    }
}
