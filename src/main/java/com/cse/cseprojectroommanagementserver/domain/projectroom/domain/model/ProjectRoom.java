package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.common.BaseTimeEntity;import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRoom extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long projectRoomId;

    private String buildingName;
    private String roomName;
}
