package com.cse.cseprojectroommanagementserver.domain.complaint.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Complaint {

    @Id @GeneratedValue
    private Long complaintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_room_id")
    private ProjectRoom projectRoom;

    private String subject;

    @Column(length = 1000)
    private String content;

    private Image image;
}
