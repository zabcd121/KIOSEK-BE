package com.cse.cseprojectroommanagementserver.integrationtest.setup;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.Complaint;
import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintRepository;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.model.ProjectRoom;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComplaintSetUp {
    @Autowired
    private ComplaintRepository complaintRepository;

    public Complaint saveComplaint(ProjectRoom projectRoom, String subject, String content, Image image) {
        return complaintRepository.save(
                Complaint.builder()
                        .projectRoom(projectRoom)
                        .subject(subject)
                        .content(content)
                        .image(image)
                        .build()
        );
    }
}
