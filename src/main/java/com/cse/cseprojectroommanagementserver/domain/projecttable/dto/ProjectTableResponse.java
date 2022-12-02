package com.cse.cseprojectroommanagementserver.domain.projecttable.dto;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import lombok.*;

public class ProjectTableResponse {

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Getter
    public static class ProjectTableSearchResponse {
        private Long tableId;
        private String tableName;

        public ProjectTableSearchResponse of(ProjectTable projectTable) {
            this.tableId = projectTable.getTableId();
            this.tableName = projectTable.getTableName();

            return this;
        }

    }
}
