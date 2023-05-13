package com.cse.cseprojectroommanagementserver.domain.complaint.domain.model;

import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.global.dto.BaseTimeEntity;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Complaint extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_room_id")
    private ProjectRoom projectRoom;

    @Column(nullable = false, length = 50)
    private String subject;

    @Column(nullable = false, length = 500)
    private String content;

    @Embedded
    private Image image;

    public static Complaint createComplaint(ProjectRoom projectRoom, String subject, String content, Image image) {
        return Complaint.builder()
                .projectRoom(projectRoom)
                .subject(subject)
                .content(content)
                .image(image)
                .build();
    }
}
