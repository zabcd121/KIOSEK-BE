package com.cse.cseprojectroommanagementserver.domain.projecttable.dto;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.*;

public class ProjectTableResDto {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ProjectTableSearchRes {
        private Long tableId;
        private String tableName;

        public ProjectTableSearchRes of(ProjectTable projectTable) {
            this.tableId = projectTable.getTableId();
            this.tableName = projectTable.getTableName();

            return this;
        }
    }
}
