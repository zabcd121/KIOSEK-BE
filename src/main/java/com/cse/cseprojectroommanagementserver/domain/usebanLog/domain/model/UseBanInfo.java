package com.cse.cseprojectroommanagementserver.domain.usebanLog.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UseBanInfo {

    @Id @GeneratedValue
    private Long useBanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_room")
    private ProjectRoom projectRoom;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

}