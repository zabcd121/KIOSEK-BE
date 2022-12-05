package com.cse.cseprojectroommanagementserver.domain.complaint.application;

import com.cse.cseprojectroommanagementserver.domain.complaint.domain.model.Complaint;
import com.cse.cseprojectroommanagementserver.domain.complaint.domain.repository.ComplaintRepository;
import com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintRequest;
import com.cse.cseprojectroommanagementserver.domain.projectroom.domain.repository.ProjectRoomRepository;
import com.cse.cseprojectroommanagementserver.global.common.Image;
import com.cse.cseprojectroommanagementserver.global.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cse.cseprojectroommanagementserver.domain.complaint.dto.ComplaintRequest.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ComplainService {

    private final ComplaintRepository complaintRepository;
    private final ProjectRoomRepository projectRoomRepository;
    private final FileUploadUtil fileUploadUtil;

    @Transactional
    public void complain(ComplainRequest complainRequest) {
        Image image = fileUploadUtil.uploadFile(complainRequest.getImage());
        Complaint complaint = Complaint.createComplaint(projectRoomRepository.getReferenceById(complainRequest.getProjectRoomId()), complainRequest.getSubject(), complainRequest.getContent(), image);

        complaintRepository.save(complaint);
    }
}
