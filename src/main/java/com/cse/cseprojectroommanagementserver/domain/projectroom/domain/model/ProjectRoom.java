package com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projecttable.domain.model.ProjectTable;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectRoom extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectRoomId;

    @Builder.Default
    @OneToMany(mappedBy = "projectRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectTable> projectTableList = new ArrayList<>();

    @Column(nullable = false, length = 10)
    private String buildingName;

    @Column(unique = true, nullable = false, length = 10)
    private String roomName;

    @Column(unique = true, nullable = false)
    private Integer priority;

}
