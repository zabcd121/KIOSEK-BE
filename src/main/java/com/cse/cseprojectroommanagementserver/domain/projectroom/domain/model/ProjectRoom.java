package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRoom extends BaseEntity {

    @Id @GeneratedValue
    private Long projectRoomId;

    private String buildingName;
    private String roomName;
}
