package com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectTable extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long tableId;

    private String tableName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_room_id")
    private ProjectRoom projectRoom;

}
