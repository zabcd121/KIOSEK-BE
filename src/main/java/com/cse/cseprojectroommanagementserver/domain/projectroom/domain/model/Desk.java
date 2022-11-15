package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model;

import com.cse.cseprojectroommanagementserver.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Desk extends BaseEntity {

    @Id @GeneratedValue
    private Long tableId;

    private Integer tableNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_room_id")
    private ProjectRoom projectRoom;
}
