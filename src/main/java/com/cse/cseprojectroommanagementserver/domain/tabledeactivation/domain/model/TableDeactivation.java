package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableDeactivation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableDeactivationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_table_id", nullable = false)
    private ProjectTable projectTable;

    @Embedded
    private TableDeactivationInfo tableDeactivationInfo;

    public static TableDeactivation createTableDeactivation(ProjectTable projectTable, TableDeactivationInfo tableDeactivationInfo) {
        return TableDeactivation.builder()
                .projectTable(projectTable)
                .tableDeactivationInfo(tableDeactivationInfo)
                .build();
    }
}
