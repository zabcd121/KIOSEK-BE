package com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TableDeactivation extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "table_deactivation_id")
    private Long tableDeactivation;

    @Embedded
    private TableDeactivationInfo tableDeactivationInfo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_table_id")
    private ProjectTable projectTable;
}
