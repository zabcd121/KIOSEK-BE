package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model;

import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

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
