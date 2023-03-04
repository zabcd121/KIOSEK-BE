package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivation;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.model.TableDeactivationInfo;
import com.cse.cseprojectroommanagementserver.domain.tabledeactivation.domain.repository.TableDeactivationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableDeactivationSetUp {

    @Autowired
    private TableDeactivationRepository tableDeactivationRepository;

    public TableDeactivation saveTableDeactivation(ProjectTable projectTable, TableDeactivationInfo tableDeactivationInfo) {
        return tableDeactivationRepository.save(
                TableDeactivation.builder()
                        .projectTable(projectTable)
                        .tableDeactivationInfo(tableDeactivationInfo)
                        .build()
        );
    }
}
