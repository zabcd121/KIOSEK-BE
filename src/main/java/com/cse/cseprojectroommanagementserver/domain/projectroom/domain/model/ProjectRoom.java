package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectRoom extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long projectRoomId;

    private String buildingName;
    private String roomName;
    private Integer priority;

}
